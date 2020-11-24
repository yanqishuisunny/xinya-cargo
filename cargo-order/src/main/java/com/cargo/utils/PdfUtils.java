package com.cargo.utils;


import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;

import java.io.*;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class PdfUtils {
    public static void main(String[] args) throws IOException {


        /*try{
            Map<String, String> params = new HashMap<>();
            params.put("pic","C:\\Users\\ASUS\\Desktop\\d7ef8a23d03f89be0008e7526e5fd8ea_t.");
            PdfDocument pdf = new PdfDocument(new PdfReader("C:\\Users\\ASUS\\Desktop\\zcp22.pdf"), new PdfWriter("C:\\Users\\ASUS\\Desktop\\zcp1.pdf"));
            if (params != null && !params.isEmpty()) {// 有参数才替换
                PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);
                Map<String, PdfFormField> fields = form.getFormFields(); // 获取所有的表单域
                for (String param : params.keySet()) {
                    PdfFormField formField = fields.get(param); // 获取某个表单域
                    if (formField != null) {
                        //formField.setFont(getDefaultFont()).setValue(params.get(param)); // 替换值

                        formField.setValue(params.get(param)); // 替换值
                    }
                }
                // form.flattenFields();// 锁定表单，不让修改
            }
            pdf.close();
        }catch (Exception  e){
            e.printStackTrace();
        }*/
        Map<String, String> params = new HashMap<>();
        params.put("pic","1|C:\\Users\\ASUS\\Desktop\\111.gif");
        PdfUtils.replaceImageFieldPdf("C:\\Users\\ASUS\\Desktop\\zcp33.pdf","C:\\Users\\ASUS\\Desktop\\zcp1.pdf",params);

    }

    /**
     * 替换PDF图片表单域（文本）变量，1、获取表单域的大小；2、根据表单域的位置，确定图片的位置；3、如果图片的宽或者高大于表单域，则等比压缩图片。
     *
     * @param templatePdfPath
     *            要替换的pdf全路径
     * @param params
     *            替换参数
     * @param destPdfPath
     *            替换后保存的PDF全路径
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static final void replaceImageFieldPdf(String templatePdfPath, String destPdfPath,
                                                  Map<String, String> params) throws FileNotFoundException, IOException {
        PdfDocument pdf = new PdfDocument(new PdfReader(templatePdfPath), new PdfWriter(destPdfPath));

        if (params != null && !params.isEmpty()) {// 有参数才替换
            PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);
            Map<String, PdfFormField> fields = form.getFormFields(); // 获取所有的表单域
            for (String param : params.keySet()) {
                PdfFormField formField = fields.get(param);
                if (formField != null) {
                    replaceFieldImage(params, pdf, param, formField); // 替换图片
                }
            }
            form.flattenFields();// 锁定表单，不让修改
        }
        pdf.close();
    }

    /**
     * 替换域中的图片
     *
     * @param params
     * @param pdf
     * @param param
     * @param formField
     * @throws MalformedURLException
     */
    private static void replaceFieldImage(Map<String, String> params, PdfDocument pdf, String param,
                                          PdfFormField formField) throws MalformedURLException {
        String value = params.get(param);
        String[] values = value.split("\\|");
        Rectangle rectangle = formField.getWidgets().get(0).getRectangle().toRectangle(); // 获取表单域的xy坐标
        PdfCanvas canvas = new PdfCanvas(pdf.getPage(Integer.parseInt(values[0])));
        ImageData image = ImageDataFactory.create(values[1]);
        float imageWidth = image.getWidth();
        float imageHeight = image.getHeight();
        float rectangleWidth = rectangle.getWidth();
        float rectangleHeight = rectangle.getHeight();

        float tempWidth = 0;
        float tempHeight = 0;

        int result = 1; // 压缩宽度
        if (imageWidth > rectangleWidth) {
            tempHeight = imageHeight * rectangleWidth / imageWidth;
            if (tempHeight > rectangleHeight) {
                tempHeight = rectangleHeight;
                result = 2; // 压缩高度
            } else {
                tempWidth = rectangleWidth;
                tempHeight = imageHeight * rectangleWidth / imageWidth;
            }
        } else {
            if (imageHeight > rectangleHeight) {
                tempHeight = rectangleHeight;
                result = 2;
            } else {
                result = 3;
            }
        }

        float y = 0;

        if (result == 1) { // 压缩宽度
            y = rectangleHeight - tempHeight;
        } else if (result == 3) { // 不压缩
            y = rectangleHeight - imageHeight;
        }

        // y/=2; // 如果想要图片在表单域的上下对齐，这个值除以2就行。同理可以计算x的偏移

        if (result == 1) {
            canvas.addImage(image, rectangle.getX(), rectangle.getY() + y, tempWidth, false);
        } else if (result == 2) {
            canvas.addImage(image, rectangle.getX(), rectangle.getY(), tempHeight, false, false);
        } else if (result == 3) {
            canvas.addImage(image, rectangle.getX(), rectangle.getY() + y, false);
        }
    }


}