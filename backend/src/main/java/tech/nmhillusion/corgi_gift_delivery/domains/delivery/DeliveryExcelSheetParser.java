package tech.nmhillusion.corgi_gift_delivery.domains.delivery;

import org.springframework.stereotype.Component;
import tech.nmhillusion.corgi_gift_delivery.entity.business.DeliveryEntity;
import tech.nmhillusion.corgi_gift_delivery.parser.ExcelSheetParser;
import tech.nmhillusion.corgi_gift_delivery.parser.RowIdxMapping;
import tech.nmhillusion.corgi_gift_delivery.util.NumberUtil;
import tech.nmhillusion.n2mix.exception.NotFoundException;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.CellData;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.RowData;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.SheetData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-07-08
 */
@Component
public class DeliveryExcelSheetParser extends ExcelSheetParser<DeliveryEntity> {

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
                rowIdxMappings = mappingIndicesForColumns(
                        dataRowCells
                        , Arrays.stream(DeliveryParserEnum.values())
                                .map(DeliveryParserEnum::getColumnName)
                                .toList()
                );

                continue;
            }

            resultList.add(
                    new DeliveryEntity()
                            .setEventId(
                                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryParserEnum.EVENT_ID.getColumnName())
                            )
                            .setDeliveryPeriodYear(
                                    Integer.parseInt(
                                            NumberUtil.parseStringFromDoubleToLong(
                                                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryParserEnum.DELIVERY_PERIOD_YEAR.getColumnName())
                                            )
                                    )
                            )
                            .setDeliveryPeriodMonth(
                                    Integer.parseInt(
                                            NumberUtil.parseStringFromDoubleToLong(
                                                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryParserEnum.DELIVERY_PERIOD_MONTH.getColumnName())
                                            )
                                    )
                            )
                            .setTerritory(
                                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryParserEnum.TERRITORY.getColumnName())
                            )
                            .setRegion(
                                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryParserEnum.REGION.getColumnName())
                            )
                            .setOrganId(
                                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryParserEnum.ORGAN_ID.getColumnName())
                            )
                            .setReceivedOrgan(
                                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryParserEnum.RECEIVED_ORGAN.getColumnName())
                            )
                            .setAmdName(
                                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryParserEnum.AMD_NAME.getColumnName())
                            )
                            .setCustomerLevel(
                                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryParserEnum.CUSTOMER_LEVEL.getColumnName())
                            )
                            .setCustomerId(
                                    NumberUtil.parseStringFromDoubleToLong(
                                            getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryParserEnum.CUSTOMER_ID.getColumnName())
                                    )
                            )
                            .setCustomerName(
                                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryParserEnum.CUSTOMER_NAME.getColumnName())
                            )
                            .setIdCardNumber(
                                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryParserEnum.ID_CARD_NUMBER.getColumnName())
                            )
                            .setPhoneNumber(
                                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryParserEnum.PHONE_NUMBER.getColumnName())
                            )
                            .setAddress(
                                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryParserEnum.ADDRESS.getColumnName())
                            )
                            .setGiftName(
                                    getValueOfColumn(dataRowCells, rowIdxMappings, DeliveryParserEnum.GIFT_NAME.getColumnName())
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
