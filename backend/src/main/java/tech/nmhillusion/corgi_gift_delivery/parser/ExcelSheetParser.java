package tech.nmhillusion.corgi_gift_delivery.parser;

import tech.nmhillusion.corgi_gift_delivery.util.CollectionUtil;
import tech.nmhillusion.n2mix.exception.NotFoundException;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.CellData;
import tech.nmhillusion.n2mix.helper.office.excel.reader.model.SheetData;
import tech.nmhillusion.n2mix.util.StringUtil;

import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2025-07-08
 */
public abstract class ExcelSheetParser<E> {
    protected String getValueOfColumn(List<CellData> dataRowCells, List<RowIdxMapping> rowIdxMappings, String columnName) {
        return dataRowCells
                .get(
                        rowIdxMappings
                                .stream().filter(it ->
                                        columnName
                                                .equals(it.columnName())
                                )
                                .findFirst()
                                .orElseThrow()
                                .columnIdx()
                )
                .getStringValue();
    }

    protected List<RowIdxMapping> mappingIndicesForColumns(List<CellData> dataRowCells, List<String> columnNameList) throws NotFoundException {
        final List<RowIdxMapping> resultList = columnNameList
                .stream()
                .map(columnName -> {
                    final int foundIdx = CollectionUtil.findIndex(dataRowCells, cellData ->
                            StringUtil.trimWithNull(cellData.getStringValue())
                                    .equalsIgnoreCase(columnName)
                    );

                    return new RowIdxMapping(columnName, foundIdx);
                })
                .toList();

        final List<RowIdxMapping> notFoundColumns = resultList.stream().filter(it -> -1 == it.columnIdx())
                .toList();
        if (!notFoundColumns.isEmpty()) {
            throw new NotFoundException("Not found columns: %s".formatted(notFoundColumns));
        }

        return resultList;
    }

    public abstract List<E> parse(SheetData sheetData) throws NotFoundException;
}
