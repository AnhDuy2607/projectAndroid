package com.example.csgt;

public class ThongTinViPham {
    private int id;
    private  String gplx;
    private  String cmnd;
    private  int solan;
    private String noidung;

    public ThongTinViPham(int id, String gplx, String cmnd, int solan, String noidung) {
        setId(id);
        setCmnd(cmnd);
        setGplx(gplx);
        setSolan(solan);
        setNoidung(noidung);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGplx() {
        return gplx;
    }

    public void setGplx(String gplx) {
        this.gplx = gplx;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public int getSolan() {
        return solan;
    }

    public void setSolan(int solan) {
        this.solan = solan;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }
}
