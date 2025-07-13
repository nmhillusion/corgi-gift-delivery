package tech.nmhillusion.corgi_gift_delivery.parser.delivery;

import org.springframework.stereotype.Component;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryEntity;
import tech.nmhillusion.corgi_gift_delivery.parser.ExcelSheetParser;
import tech.nmhillusion.corgi_gift_delivery.parser.RowIdxMapping;
import tech.nmhillusion.corgi_gift_delivery.util.CollectionUtil;
import tech.nmhillusion.corgi_gift_delivery.util.NumberUtil;
import tech.nmhillusion.n2mix.exception.NotFoundException;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.CellData;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.RowData;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.SheetData;
import tech.nmhillusion.n2mix.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-07-08
 */
@Component
public class DeliveryExcelSheetParser implements ExcelSheetParser<DeliveryEntity> {
    private static List<RowIdxMapping> mappingIndicesForColumns(List<CellData> dataRowCells) {
        return Arrays.stream(DeliveryParserEnum.values())
                .map(deliveryParserEnum -> {
                    final String columnName = deliveryParserEnum.getColumnName();

                    final int foundIdx = CollectionUtil.findIndex(dataRowCells, cellData ->
                            StringUtil.trimWithNull(cellData.getStringValue())
                                    .equalsIgnoreCase(columnName)
                    );

                    return new RowIdxMapping(columnName, foundIdx);
                })
                .toList();
    }

    private static String getValueOfColumn(List<CellData> dataRowCells, List<RowIdxMapping> rowIdxMappings, DeliveryParserEnum deliveryParserEnum) {
        return dataRowCells
                .get(
                        rowIdxMappings
                                .stream().filter(it ->
                                        deliveryParserEnum
                                                .getColumnName()
                                                .equals(it.columnName())
                                )
                                .findFirst()
                                .orElseThrow()
                                .columnIdx()
                )
                .getStringValue();
    }

    @Override
    public List<DeliveryEntity> parse(SheetData sheetData) throws NotFoundException {
        final List<RowData> dataRows = sheetData.getRows();
        final List<DeliveryEntity> resultList = new ArrayList<>();

        List<RowIdxMapping> rowIdxMappings = null;

        for (RowData dataRow : dataRows) {
            final List<CellData> dataRowCells = dataRow.getCells();

            if (dataRowCells.size() < DeliveryParserEnum.values().length) {
                continue;
            }

            if (null == rowIdxMappings) {
                rowIdxMappings = mappingIndicesForColumns(dataRowCells);

                final List<RowIdxMapping> notFoundColumns = rowIdxMappings.stream().filter(it -> it.columnIdx() == -1)
                        .toList();
                if (!notFoundColumns.isEmpty()) {
                    throw new NotFoundException("Not found columns: %s".formatted(notFoundColumns));
                }

                continue;
            }

            resultList.add(
                    new DeliveryEntity()
                            .setEventId(
                                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryParserEnum.EVENT_ID)
                            )
                            .setDeliveryPeriodYear(
                                    Integer.parseInt(
                                            NumberUtil.parseStringFromDoubleToLong(
                                                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryParserEnum.DELIVERY_PERIOD_YEAR)
                                            )
                                    )
                            )
                            .setDeliveryPeriodMonth(
                                    Integer.parseInt(
                                            NumberUtil.parseStringFromDoubleToLong(
                                                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryParserEnum.DELIVERY_PERIOD_MONTH)
                                            )
                                    )
                            )
                            .setTerritory(
                                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryParserEnum.TERRITORY)
                            )
                            .setRegion(
                                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryParserEnum.REGION)
                            )
                            .setOrganId(
                                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryParserEnum.ORGAN_ID)
                            )
                            .setReceivedOrgan(
                                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryParserEnum.RECEIVED_ORGAN)
                            )
                            .setAmdName(
                                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryParserEnum.AMD_NAME)
                            )
                            .setCustomerLevel(
                                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryParserEnum.CUSTOMER_LEVEL)
                            )
                            .setCustomerId(
                                    NumberUtil.parseStringFromDoubleToLong(
                                            getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryParserEnum.CUSTOMER_ID)
                                    )
                            )
                            .setCustomerName(
                                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryParserEnum.CUSTOMER_NAME)
                            )
                            .setIdCardNumber(
                                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryParserEnum.ID_CARD_NUMBER)
                            )
                            .setPhoneNumber(
                                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryParserEnum.PHONE_NUMBER)
                            )
                            .setAddress(
                                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryParserEnum.ADDRESS)
                            )
                            .setGiftName(
                                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryParserEnum.GIFT_NAME)
                            )
                            .setNote(
                                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryParserEnum.NOTE)
                            )
            )
            ;
        }

        return resultList;
    }
}
