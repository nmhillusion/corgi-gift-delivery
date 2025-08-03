package tech.nmhillusion.corgi_gift_delivery.domains.deliveryReturn;

import org.springframework.stereotype.Component;
import tech.nmhillusion.corgi_gift_delivery.domains.delivery.DeliveryService;
import tech.nmhillusion.corgi_gift_delivery.domains.deliveryAttempt.DeliveryAttemptParserEnum;
import tech.nmhillusion.corgi_gift_delivery.domains.deliveryAttempt.DeliveryAttemptService;
import tech.nmhillusion.corgi_gift_delivery.domains.deliveryReturnStatus.DeliveryReturnStatusService;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryReturnEntity;
import tech.nmhillusion.corgi_gift_delivery.parser.ExcelSheetParser;
import tech.nmhillusion.corgi_gift_delivery.parser.RowIdxMapping;
import tech.nmhillusion.n2mix.exception.NotFoundException;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.CellData;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.RowData;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.SheetData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * created by: minguy1
 * <p>
 * created date: 2025-07-19
 */
@Component
public class DeliveryReturnExcelSheetParser extends ExcelSheetParser<DeliveryReturnEntity> {
    private final DeliveryService deliveryService;
    private final DeliveryAttemptService deliveryAttemptService;
    private final DeliveryReturnStatusService deliveryReturnStatusService;


    public DeliveryReturnExcelSheetParser(DeliveryService deliveryService, DeliveryAttemptService deliveryAttemptService, DeliveryReturnStatusService deliveryReturnStatusService) {
        this.deliveryService = deliveryService;
        this.deliveryAttemptService = deliveryAttemptService;
        this.deliveryReturnStatusService = deliveryReturnStatusService;
    }

    @Override
    public List<DeliveryReturnEntity> parse(SheetData sheetData) throws NotFoundException {
        final List<RowData> dataRows = sheetData.getRows();
        final List<DeliveryReturnEntity> resultList = new ArrayList<>();

        List<RowIdxMapping> rowIdxMappings = null;

        for (RowData dataRow : dataRows) {
            final List<CellData> dataRowCells = dataRow.getCells();

            if (dataRowCells.size() < DeliveryReturnParserEnum.values().length) {
                continue;
            }

            if (null == rowIdxMappings) {
                rowIdxMappings = mappingIndicesForColumns(
                        dataRowCells
                        , Arrays.stream(DeliveryReturnParserEnum.values())
                                .map(DeliveryReturnParserEnum::getColumnName)
                                .toList()
                );

                continue;
            }

            final String eventId = getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryAttemptParserEnum.EVENT_ID.getColumnName());
            final String customerId = getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryAttemptParserEnum.CUSTOMER_ID.getColumnName());
            final Long deliveryId = deliveryService.getDeliveryIdByEventAndCustomer(eventId, customerId);

            final String returnStatus = getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryReturnParserEnum.RETURN_STATUS.getColumnName());

            resultList.add(
                    new DeliveryReturnEntity()
                            .setDeliveryId(
                                    deliveryId
                            )
                            .setAttemptId(
                                    deliveryAttemptService.getMaxAttemptIdOfDeliveryId(deliveryId)
                            )
                            .setReturnStatusId(
                                    Integer.parseInt(
                                            deliveryReturnStatusService.getDeliveryReturnStatusByStatusName(returnStatus).getStatusId()
                                    )
                            )
                            .setNote(
                                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryReturnParserEnum.NOTE.getColumnName())
                            )
            );
        }

        return resultList;
    }
}
