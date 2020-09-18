package com.workspace.adminpanels.Model;

public class IncomeModel {
    Integer idPembayaran;
    Integer total;

    public IncomeModel() {
    }

    public IncomeModel(Integer idPembayaran, Integer totalr) {
        this.idPembayaran = idPembayaran;
        this.total = total;;
    }

    public Integer getIdPembayaran() {
        return idPembayaran;
    }

    public void setIdPembayaran(Integer idPembayaran) {
        this.idPembayaran = idPembayaran;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
