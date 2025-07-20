package tech.nmhillusion.corgi_gift_delivery.domains.base;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.corgi_gift_delivery.entity.business.BaseBusinessEntity;
import tech.nmhillusion.n2mix.exception.ApiResponseException;

import java.util.List;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */
public interface BaseDeliveryService<E extends BaseBusinessEntity<Long>, DTO> {
    List<E> insertBatchByExcelFile(MultipartFile excelFile) throws ApiResponseException;

    List<E> updateBatchByExcelFile(MultipartFile excelFile) throws ApiResponseException;

    Page<E> search(DTO dto, int pageIndex, int pageSize);
}
