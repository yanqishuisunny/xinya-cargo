package com.cargo.invoice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cargo.invoice.dto.InvoiceDto;
import com.cargo.invoice.entity.InvoiceEntity;
import com.cargo.invoice.vo.InvoiceVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 税票表 Mapper 接口
 * </p>
 *
 * @author 开发者
 * @since 2020-11-09
 */
@Repository
public interface InvoiceMapper extends BaseMapper<InvoiceEntity> {

    List<InvoiceVo> invoiceList(@Param("dto") InvoiceDto dto, Page<InvoiceVo> pag);
}
