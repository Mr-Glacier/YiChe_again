package Entity;

public class Model_Url {
    private int C_ID;
    private String brand_id;
    private String brand_name;
    private String page_url;
    private String downState;
    private String page_number;

    public String getpage_number() {
        return page_number;
    }

    public void setpage_number(String page_number) {
        this.page_number = page_number;
    }

    public int getC_ID() {
        return C_ID;
    }

    public void setC_ID(int c_ID) {
        C_ID = c_ID;
    }

    public String getbrand_id() {
        return brand_id;
    }

    public void setbrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getbrand_name() {
        return brand_name;
    }

    public void setbrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getpage_url() {
        return page_url;
    }

    public void setpage_url(String page_url) {
        this.page_url = page_url;
    }

    public String getdownstate() {
        return downState;
    }

    public void setdownstate(String downstate) {
        this.downState = downstate;
    }
}
