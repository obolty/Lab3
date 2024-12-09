package bsu.rfe.java.group6.lab3.Mazanik.varA;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class GornerTableModel extends AbstractTableModel {
    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;

    public GornerTableModel(Double from, Double to, Double step, Double[] coefficients) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.coefficients = coefficients;
    }

    public Double getFrom() {
        return from;
    }

    public Double getTo() {
        return to;
    }

    public Double getStep() {
        return step;
    }

    public int getColumnCount() {
        return 3;
    }

    public int getRowCount() {
        return new Double(Math.ceil((to - from) / step)).intValue() + 1;
    }

    public Object getValueAt(int row, int col) {
        double x = from + step * row;
        if (col == 0) {
            return x;
        } else if (col == 1) {
            double result = coefficients[0];
            double roundedResult = 0;
            for (int i = 1; i < coefficients.length; i++) {
                result = result * x + coefficients[i];
                roundedResult = (double) Math.round(result * 100) / 100; // Округляем до двух знаков
            }
            return roundedResult;
        } else {
            double result = coefficients[0];
            for (int i = 1; i < coefficients.length; i++) {
                result = result * x + coefficients[i];
            }

            double roundedResult = Math.abs((double) Math.round(result * 100) / 100); // Округляем до двух знаков

            double fractionalPart = roundedResult - Math.floor(roundedResult);

            // Проверка на то, что дробная часть - квадрат целого числа
            for (int i = 0; i * i <= 100; i++) { // Перебираем целые числа до 100 (квадрат до 10000)
                if (Math.abs((double)i*i/100 - fractionalPart) < 1e-9) { // Сравниваем с учетом погрешности
                    return true;
                }
            }
            return false;
        }
    }

    public String getColumnName(int col) {
        switch (col) {
            case 0:
                return "Значение X";
            case 1:
                return "Значение многочлена";
            default:
                return "Дробная часть является квадратом?";
        }
    }

    public Class<?> getColumnClass(int col) {
        switch (col) {
            case 0:
            case 1:
                return Double.class;
            default:
                return Boolean.class;
        }
    }
}