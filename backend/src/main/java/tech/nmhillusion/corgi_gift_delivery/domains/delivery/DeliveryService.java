package tech.nmhillusion.corgi_gift_delivery.domains.delivery;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryEntity;
import tech.nmhillusion.corgi_gift_delivery.entity.business.export.LatestDeliveryReportEntity;
import tech.nmhillusion.corgi_gift_delivery.service.business.BaseBusinessService;
import tech.nmhillusion.corgi_gift_delivery.service.business.BaseDeliveryService;
import tech.nmhillusion.n2mix.exception.ApiResponseException;

import java.util.List;
import java.util.Optional;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-07-06
 */
public interface DeliveryService extends BaseBusinessService<DeliveryEntity>, BaseDeliveryService<DeliveryEntity, DeliverySearchDto> {
    Optional<Long> getDeliveryIdByEventAndCustomer(String eventId, String customerId);

    List<DeliveryEntity> insertBatchByExcelFile(MultipartFile excelFile) throws ApiResponseException;

    List<DeliveryEntity> updateBatchByExcelFile(MultipartFile excelFile);

    Page<DeliveryEntity> search(DeliverySearchDto deliveryDto, int pageIndex, int pageSize);

    String getCustomerNameOfDelivery(String deliveryId, String customerId);

    Resource exportSummaryDeliveries(DeliverySearchDto deliveryDto);

    LatestDeliveryReportEntity convertToLatestDeliveryReport(DeliveryEntity deliveryEntity);
}
