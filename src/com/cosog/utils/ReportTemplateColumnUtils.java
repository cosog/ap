package com.cosog.utils;

import java.util.*;

import com.cosog.model.ReportTemplate;

public class ReportTemplateColumnUtils {
    /**
     * 根据 template.hiddenColumns 物理删除列，并同步调整表头、列宽、合并单元格、可编辑区域
     */
    public static void removeHiddenColumns(ReportTemplate reportTemplate) {
        if (reportTemplate == null) {
            return;
        }
        processClasses0(reportTemplate.getClasses0());
        processTemplate(reportTemplate.getClasses1());
        processClasses0(reportTemplate.getClasses2());
    }

    private static void processClasses0(ReportTemplate.Classes0 classes0) {
        if (classes0 == null) {
            return;
        }
        processTemplateList(classes0.getSingleWellRangeReportTemplate());
        processTemplateList(classes0.getSingleWellDailyReportTemplate());
        processTemplateList(classes0.getProductionReportTemplate());
    }

    private static void processTemplateList(List<ReportTemplate.Template> list) {
        if (list == null) {
            return;
        }
        for (ReportTemplate.Template template : list) {
            processTemplate(template);
        }
    }

    private static void processTemplate(ReportTemplate.Template template) {
        if (template == null) {
            return;
        }

        List<Integer> hiddenColumns = template.getHiddenColumns();
        if (hiddenColumns == null || hiddenColumns.isEmpty()) {
            return;
        }

        int maxCols = getMaxColumnCount(template);

        // 去重、过滤非法列
        TreeSet<Integer> hiddenSet = new TreeSet<>();
        for (Integer col : hiddenColumns) {
            if (col != null && col >= 0 && (maxCols <= 0 || col < maxCols)) {
                hiddenSet.add(col);
            }
        }

        if (hiddenSet.isEmpty()) {
            template.setHiddenColumns(new ArrayList<Integer>());
            return;
        }

        // 删除 List 元素时要从后往前删，避免索引错位
        List<Integer> hiddenDesc = new ArrayList<>(hiddenSet);
        Collections.sort(hiddenDesc, Collections.reverseOrder());

        // 1. 删除表头中对应列
        if (template.getHeader() != null) {
            for (ReportTemplate.Header header : template.getHeader()) {
                removeIndexes(header.getTitle_zh_CN(), hiddenDesc);
                removeIndexes(header.getTitle_en(), hiddenDesc);
                removeIndexes(header.getTitle_ru(), hiddenDesc);
            }
        }

        // 2. 删除列宽
        removeIndexes(template.getColumnWidths_zh_CN(), hiddenDesc);
        removeIndexes(template.getColumnWidths_en(), hiddenDesc);
        removeIndexes(template.getColumnWidths_ru(), hiddenDesc);

        // 3. 调整合并单元格
        adjustMergeCells(template, hiddenSet);

        // 4. 调整可编辑区域
        adjustEditables(template, hiddenSet);

        // 5. 已物理删除，清空 hiddenColumns，避免前端再次隐藏新列
        template.setHiddenColumns(new ArrayList<Integer>());
    }

    private static int getMaxColumnCount(ReportTemplate.Template template) {
        int max = 0;
        max = Math.max(max, size(template.getColumnWidths_zh_CN()));
        max = Math.max(max, size(template.getColumnWidths_en()));
        max = Math.max(max, size(template.getColumnWidths_ru()));

        if (template.getHeader() != null) {
            for (ReportTemplate.Header header : template.getHeader()) {
                max = Math.max(max, size(header.getTitle_zh_CN()));
                max = Math.max(max, size(header.getTitle_en()));
                max = Math.max(max, size(header.getTitle_ru()));
            }
        }
        return max;
    }

    private static int size(List<?> list) {
        return list == null ? 0 : list.size();
    }

    private static <T> void removeIndexes(List<T> list, List<Integer> hiddenDesc) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (Integer idx : hiddenDesc) {
            if (idx != null && idx >= 0 && idx < list.size()) {
                list.remove(idx.intValue());
            }
        }
    }

    private static void adjustMergeCells(ReportTemplate.Template template, Set<Integer> hiddenSet) {
        List<ReportTemplate.MergeCells> mergeCells = template.getMergeCells();
        if (mergeCells == null || mergeCells.isEmpty()) {
            return;
        }

        List<ReportTemplate.MergeCells> newMergeCells = new ArrayList<>();

        for (ReportTemplate.MergeCells mc : mergeCells) {
            int oldCol = mc.getCol();
            int oldColspan = mc.getColspan();

            if (oldColspan <= 0) {
                newMergeCells.add(mc);
                continue;
            }

            int oldEnd = oldCol + oldColspan - 1;

            // 计算原合并区域内，删除隐藏列后还剩多少列
            int newColspan = 0;
            for (int c = oldCol; c <= oldEnd; c++) {
                if (!hiddenSet.contains(c)) {
                    newColspan++;
                }
            }

            // 整个合并区域都被删掉
            if (newColspan <= 0) {
                continue;
            }

            // 新的起始列 = 原起始列 - 左侧被删列数量
            int newCol = oldCol - countHiddenBefore(hiddenSet, oldCol);

            mc.setCol(newCol);
            mc.setColspan(newColspan);
            newMergeCells.add(mc);
        }

        template.setMergeCells(newMergeCells);
    }

    private static void adjustEditables(ReportTemplate.Template template, Set<Integer> hiddenSet) {
        List<ReportTemplate.Editable> editables = template.getEditable();
        if (editables == null || editables.isEmpty()) {
            return;
        }

        List<ReportTemplate.Editable> newEditables = new ArrayList<>();

        for (ReportTemplate.Editable editable : editables) {
            int oldStart = editable.getStartColumn();
            int oldEnd = editable.getEndColumn();

            int newStart = oldStart - countHiddenBefore(hiddenSet, oldStart);
            int newEnd = oldEnd - countHiddenLessOrEqual(hiddenSet, oldEnd);

            if (newStart > newEnd) {
                continue;
            }

            // 注意：你当前模型里 Editable 的 setter 拼写是 setStattColumn
            // 如果以后修正为 setStartColumn，这里同步修改即可
            editable.setStattColumn(newStart);
            editable.setEndColumn(newEnd);

            newEditables.add(editable);
        }

        template.setEditable(newEditables);
    }

    private static int countHiddenBefore(Set<Integer> hiddenSet, int col) {
        int count = 0;
        for (Integer h : hiddenSet) {
            if (h < col) {
                count++;
            }
        }
        return count;
    }

    private static int countHiddenLessOrEqual(Set<Integer> hiddenSet, int col) {
        int count = 0;
        for (Integer h : hiddenSet) {
            if (h <= col) {
                count++;
            }
        }
        return count;
    }
}
