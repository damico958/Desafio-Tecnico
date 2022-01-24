package com.damico958.desafio_tecnico.model;

public class FileReport {
    private int salesmenAmount;
    private int customersAmount;
    private Sale mostExpensiveSale;
    private Salesman worstSalesmanEver;

    public int getSalesmenAmount() {
        return salesmenAmount;
    }

    public void setSalesmenAmount(int salesmenAmount) {
        this.salesmenAmount = salesmenAmount;
    }

    public int getCustomersAmount() {
        return customersAmount;
    }

    public void setCustomersAmount(int customersAmount) {
        this.customersAmount = customersAmount;
    }

    public Sale getMostExpensiveSale() {
        return mostExpensiveSale;
    }

    public void setMostExpensiveSale(Sale mostExpensiveSale) {
        this.mostExpensiveSale = mostExpensiveSale;
    }

    public Salesman getWorstSalesmanEver() {
        return worstSalesmanEver;
    }

    public void setWorstSalesmanEver(Salesman worstSalesmanEver) {
        this.worstSalesmanEver = worstSalesmanEver;
    }

    @Override
    public String toString() {
        return  "----------------------------------------------------\n" +
                "Reporting relevant data from file" +
                "\n----------------------------------------------------\n" +
                "Salesmen amount: " + salesmenAmount +
                "\n----------------------------------------------------\n" +
                "Customers amount: " + customersAmount +
                "\n----------------------------------------------------\n" +
                "Most expensive sale: \n\n" + mostExpensiveSale +
                "\n----------------------------------------------------\n" +
                "Worst salesman ever: \n" + worstSalesmanEver + "\n";
    }
}
