import Dao.Dao_sqlsever;
import Entity.Bean_Brand;
import Entity.Model_Url;
import Until.ReadUntil;
import Until.SaveUntil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;

public class Anslysis {
    Dao_sqlsever dao_sqlsever = new Dao_sqlsever("T_YiCheBrand", 0);
    Dao_sqlsever dao_sqlsever1 = new Dao_sqlsever("T_YiCheBrand_page", 1);
    private SaveUntil saveUntil = new SaveUntil();
    private ReadUntil readUntil = new ReadUntil();

    public void Method_下载方法(String url, String savePath, String saveName) {
        try {
            Document document = Jsoup.parse(new URL(url).openStream(), "UTF-8", url);
            Thread.sleep(1000);
            saveUntil.Method_SaveFile(savePath + saveName, document.toString());
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public void Method_1_下载品牌文件(String url, String path, String filename) {
        Method_下载方法(url, path, filename);
    }

    public void Method_2_解析所有品牌并入库(String path_name) {
        String Content = readUntil.Method_ReadFile(path_name);
        Document document = Jsoup.parse(Content);
        Elements Items = document.select(".choose-car").select(".brand-list-content");
        Elements Items2 = Items.select(".brand-list").select(".brand-list-item");
        //System.out.println(Items2.size());
        for (int i = 0; i < Items2.size(); i++) {
            Elements brand_items = Items2.get(i).select(".item-brand");
            for (int j = 0; j < brand_items.size(); j++) {
                Element brand_item = brand_items.get(j);
                String brand_id = brand_item.attr("data-id");
                String brand_name = brand_item.attr("data-name");
                String brand_url = "https://car.yiche.com" + brand_item.select("a").attr("href");
                String brand_pictureurl = "https:" + brand_item.select(".brand-icon.lazyload").attr("data-original");
                System.out.println(brand_pictureurl);
                Bean_Brand bean_brand = new Bean_Brand();
                bean_brand.setbrand_id(brand_id);
                bean_brand.setbrand_name(brand_name);
                bean_brand.setbrand_url(brand_url);
                bean_brand.setbrand_pictureurl(brand_pictureurl);
                dao_sqlsever.Method_Insert2(bean_brand);
            }
        }
    }

    public void Method_3_下载所有品牌html文件(String savePath) {

        ArrayList<Object> brandList = dao_sqlsever.Method_Find();
        for (int i = 0; i < brandList.size(); i++) {
            String brand_name = ((Bean_Brand) brandList.get(i)).getbrand_name();
            System.out.println(brand_name);
            String brand_url = ((Bean_Brand) brandList.get(i)).getbrand_url();
            Method_下载方法(brand_url, savePath, brand_name.replace(":", "_") + ".txt");
        }
    }

    public void Method_4_解析所有的品牌html(String path) {
        ArrayList<Object> brandList = dao_sqlsever.Method_Find();
        for (int i = 0; i < brandList.size(); i++) {
            String brand_name = ((Bean_Brand) brandList.get(i)).getbrand_name();
            String brand_url = ((Bean_Brand) brandList.get(i)).getbrand_url();
            String brand_id = ((Bean_Brand) brandList.get(i)).getbrand_id();
            String content = readUntil.Method_ReadFile(path + brand_name.replace(":", "_") + ".txt");
            Document document = Jsoup.parse(content);
            Elements Items1 = document.select(".pagination-list-wrapper").select(".link-list.pg-item");
            if (Items1.size() != 0) {
                System.out.println("buweikong");
                System.out.println(Items1.size());
                Elements Items2 = Items1.select("a");
                System.out.println(Items2.size());
                String model_url = "";
                for (int j = 0; j < Items2.size(); j++) {
                    if (j == 0) {
                        model_url = brand_url;
                    } else {
                        model_url = brand_url + "&page=" + (j + 1);
                    }
                    Model_Url model_url1 = new Model_Url();
                    model_url1.setbrand_id(brand_id);
                    model_url1.setbrand_name(brand_name);
                    model_url1.setpage_url(model_url);
                    model_url1.setdownstate("否");
                    model_url1.setpage_number(String.valueOf(j+1));
                    dao_sqlsever1.Method_Insert2(model_url1);
                }

            }

        }
    }
    public void Method_5_下载所有的品牌分页_model(String path){
        ArrayList<Object> brand_pageList = dao_sqlsever1.Method_Find();
        for (int i = 0; i < brand_pageList.size(); i++) {
            String page_number = ((Model_Url)brand_pageList.get(i)).getpage_number();
            String brand_name = ((Model_Url)brand_pageList.get(i)).getbrand_name();
            String page_url = ((Model_Url)brand_pageList.get(i)).getpage_url();
            Method_下载方法(page_url, path, (brand_name+page_number).replace(":", "")
                    +".txt");
        }
    }
    public void Method_6_解析所有的分页数据入库车型(){

    }

    public static void main(String[] args) {
        Anslysis AS = new Anslysis();
        String allbrand_savePath = "F:\\A_易车数据_811\\";
        String allbrand_filename = "所有品牌.txt";
        //AS.Method_1_下载品牌文件("https://car.yiche.com/",allbrand_savePath,allbrand_filename);
        //AS.Method_2_解析所有品牌并入库(allbrand_savePath+allbrand_filename);
        String save_brand_hrml = "F:\\A_易车数据_811\\品牌文件\\";
        //AS.Method_3_下载所有品牌html文件(save_brand_hrml);
        //AS.Method_4_解析所有的品牌html(save_brand_hrml);
        String Brand_page_path ="F:\\A_易车数据_811\\品牌分页\\";
        //AS.Method_5_下载所有的品牌分页_model(Brand_page_path);
        AS.Method_6_解析所有的分页数据入库车型();

    }
}
