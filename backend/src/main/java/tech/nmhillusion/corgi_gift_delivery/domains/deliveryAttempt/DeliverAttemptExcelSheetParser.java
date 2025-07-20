package tech.nmhillusion.corgi_gift_delivery.domains.deliveryAttempt;

import org.springframework.stereotype.Component;
import tech.nmhillusion.corgi_gift_delivery.domains.delivery.DeliveryParserEnum;
import tech.nmhillusion.corgi_gift_delivery.domains.delivery.DeliveryService;
import tech.nmhillusion.corgi_gift_delivery.domains.deliveryStatus.DeliveryStatusService;
import tech.nmhillusion.corgi_gift_delivery.domains.deliveryType.DeliveryTypeService;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliverAttemptEntity;
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
public class DeliverAttemptExcelSheetParser extends ExcelSheetParser<DeliverAttemptEntity> {
    private final DeliveryService deliveryService;
    private final DeliveryTypeService deliveryTypeService;
    private final DeliveryStatusService deliveryStatusService;

    public DeliverAttemptExcelSheetParser(DeliveryService deliveryService, DeliveryTypeService deliveryTypeService, DeliveryStatusService deliveryStatusService) {
        this.deliveryService = deliveryService;
        this.deliveryTypeService = deliveryTypeService;
        this.deliveryStatusService = deliveryStatusService;
    }

    @Override
    public List<DeliverAttemptEntity> parse(SheetData sheetData) throws NotFoundException {
        final List<RowData> dataRows = sheetData.getRows();
        final List<DeliverAttemptEntity> resultList = new ArrayList<>();

        List<RowIdxMapping> rowIdxMappings = null;

        for (RowData dataRow : dataRows) {
            final List<CellData> dataRowCells = dataRow.getCells();

            if (dataRowCells.size() < DeliverAttemptParserEnum.values().length) {
                continue;
            }

            if (null == rowIdxMappings) {
                rowIdxMappings = mappingIndicesForColumns(
                        dataRowCells
                        , Arrays.stream(DeliverAttemptParserEnum.values())
                                .map(DeliverAttemptParserEnum::getColumnName)
                                .toList()
                );

                continue;
            }

            final String eventId = getValueOfColumn(dataRowCells, rowIdxMappings, DeliverAttemptParserEnum.EVENT_ID.getColumnName());
            final String customerId = getValueOfColumn(dataRowCells, rowIdxMappings, DeliverAttemptParserEnum.CUSTOMER_ID.getColumnName());
            final Long deliveryId = deliveryService.getDeliveryIdByEventAndCustomer(eventId, customerId);

            final String deliveryType = getValueOfColumn(dataRowCells, rowIdxMappings, DeliverAttemptParserEnum.DELIVERY_TYPE.getColumnName());
            final String deliveryStatus = getValueOfColumn(dataRowCells, rowIdxMappings, DeliverAttemptParserEnum.DELIVERY_STATUS.getColumnName());

            resultList.add(
                    new DeliverAttemptEntity()
                            .setDeliveryId(
                                    deliveryId
                            )
                            .setDeliveryTypeId(
                                    Integer.parseInt(
                                            deliveryTypeService.getDeliveryTypeByTypeName(deliveryType).getTypeId()
                                    )
                            )
                            .setDeliveryStatusId(
                                    Integer.parseInt(
                                            deliveryStatusService.getDeliveryStatusByStatusName(deliveryStatus).getStatusId()
                                    )
                            )
                            .setNote(
                                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryParserEnum.NOTE.getColumnName())
                            )
            )
            ;
        }

        return resultList;
    }

}
