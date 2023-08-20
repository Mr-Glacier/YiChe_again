import Dao.Dao_sqlsever;
import Entity.Bean_Brand;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;

public class Main {
    public static void main(String[] args) {
        Dao_sqlsever dao_sqlsever = new Dao_sqlsever("T_YiCheBrand", 0);
        String url = "https://car.yiche.com/";
        try{
            Document document = Jsoup.parse(new URL(url).openStream(),"UTF-8",url);
            //System.out.println(document.toString());
            Elements Items = document.select(".choose-car").select(".brand-list-content");
            Elements Items2 = Items.select(".brand-list").select(".brand-list-item");
            //System.out.println(Items2.size());
            for (int i = 0; i < Items2.size(); i++) {
                Elements brand_items = Items2.get(i).select(".item-brand");
                for (int j = 0; j < brand_items.size(); j++) {
                    Element brand_item = brand_items.get(j);
                    String brand_id = brand_item.attr("data-id");
                    String brand_name = brand_item.attr("data-name");
                    String brand_url = "https://car.yiche.com"+brand_item.select("a").attr("href");
                    String brand_pictureurl ="https:"+brand_item.select(".brand-icon.lazyload").attr("data-original");
                    System.out.println(brand_pictureurl);
                    Bean_Brand bean_brand = new Bean_Brand();
                    bean_brand.setbrand_id(brand_id);
                    bean_brand.setbrand_name(brand_name);
                    bean_brand.setbrand_url(brand_url);
                    bean_brand.setbrand_pictureurl(brand_pictureurl);
                    dao_sqlsever.Method_Insert2(bean_brand);
                }
            }
        }catch (Exception ex){
            System.out.println(ex.toString());
        }

        //F:\A_易车数据_811\品牌文件

//T_YiCheBrand

        System.out.println("Hello world!");
    }
}