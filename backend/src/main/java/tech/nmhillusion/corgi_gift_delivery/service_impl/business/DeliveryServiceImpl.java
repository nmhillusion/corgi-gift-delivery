package tech.nmhillusion.corgi_gift_delivery.service_impl.business;

import org.springframework.web.multipart.MultipartFile;
import tech.nmhillusion.corgi_gift_delivery.annotation.TransactionalService;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryEntity;
import tech.nmhillusion.corgi_gift_delivery.repository.business.DeliveryRepository;
import tech.nmhillusion.corgi_gift_delivery.service.business.DeliveryService;
import tech.nmhillusion.corgi_gift_delivery.service.core.SequenceService;
import tech.nmhillusion.n2mix.helper.office.excel.reader.ExcelReader;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.SheetData;

import java.io.IOException;
import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-07-06
 */

@TransactionalService
public class DeliveryServiceImpl extends BaseBusinessServiceImpl<DeliveryEntity, DeliveryRepository> implements DeliveryService {
    private final DeliveryRepository deliveryRepository;


    public DeliveryServiceImpl(DeliveryRepository deliveryRepository, SequenceService sequenceService) {
        super(deliveryRepository, sequenceService);
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    public Long getDeliveryIdByEventAndCustomer(String eventId, String customerId) {
        return deliveryRepository.findByEventIdAndCustomerId(eventId, customerId)
                .getDeliveryId();
    }

    @Override
    public Iterable<DeliveryEntity> saveBatchByExcelFile(MultipartFile excelFile) throws IOException {
        final List<SheetData> sheetList = ExcelReader.read(excelFile.getInputStream());

        /// TODO: 2025-07-06 impl parser for entity from excel 

        return null;
    }
}
