package kg.tech.tradebackend.utils;

import kg.tech.tradebackend.domain.entities.Image;
import kg.tech.tradebackend.domain.entities.Item;
import kg.tech.tradebackend.domain.enums.Color;
import kg.tech.tradebackend.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Component
public class InsertDataToDatabase {
    @Autowired ItemRepository itemRepository;

    /***
     * @throws IOException
     * Закомментировать после первого запуска
     */
    @PostConstruct
    public void init() throws IOException {
//        List<Image> images = new ArrayList<>(3);
//        byte[] img1 = Files.readAllBytes(ResourceUtils.getFile("classpath:static/images/item1-3.png").toPath());
//        images.add(new Image(null, img1, true));
//
//        byte[] img1_2 = Files.readAllBytes(ResourceUtils.getFile("classpath:static/images/item1-1.png").toPath());
//        images.add(new Image(null, img1_2, false));
//
//        byte[] img1_3 = Files.readAllBytes(ResourceUtils.getFile("classpath:static/images/item1-2.png").toPath());
//        images.add(new Image(null, img1_3, false));
//
//        Item item = new Item("LIGE", BigDecimal.valueOf(2392), 120, 5d, Color.GREEN, "Кварц" ,
//                "мужские водонепроницаемые часы 30ATM с датой", "железные",100, "LIGE Лидирующий бренд, роскошные модные часы для дайверов, мужские водонепроницаемые часы 30ATM с датой, спортивные часы, мужские кварцевые наручные часы, Relogio Masculino",
//                true, images);
//
//        itemRepository.save(item);
//
//        //--------------------------------------------------------------------------------------------------------------
//        List<Image> images2 = new ArrayList<>(3);
//        byte[] img2_1 = Files.readAllBytes(ResourceUtils.getFile("classpath:static/images/item2-1.png").toPath());
//        images2.add(new Image(null, img2_1, true));
//
//        byte[] img2_2 = Files.readAllBytes(ResourceUtils.getFile("classpath:static/images/item2-2.png").toPath());
//        images2.add(new Image(null, img2_2, false));
//
//        Item item2 = new Item("Часы унисекс", BigDecimal.valueOf(239,20), 120, 5d, Color.GREEN, "Кварц" ,
//                "водонепроницаемые", "нейлон",100, "Часы унисекс из нейлоновой ткани, для детей, спортивные, тонкие, холщовые, кварцевые наручные часы", true, images2);
//
//        itemRepository.save(item2);
//
//
//        //--------------------------------------------------------------------------------------------------------------
//        List<Image> images3 = new ArrayList<>(3);
//        byte[] img3_1 = Files.readAllBytes(ResourceUtils.getFile("classpath:static/images/item3-1.jpg").toPath());
//        images3.add(new Image(null, img3_1, true));
//
//        byte[] img3_2 = Files.readAllBytes(ResourceUtils.getFile("classpath:static/images/item3-2.jpg").toPath());
//        images3.add(new Image(null, img3_2, false));
//
//        byte[] img3_3 = Files.readAllBytes(ResourceUtils.getFile("classpath:static/images/item3-3.png").toPath());
//        images3.add(new Image(null, img3_3, false));
//
//        Item item3 = new Item("Molumenzeit S 7", BigDecimal.valueOf(15,588), 120, 5d, Color.BLUE, "Сапфир Кристал" ,
//                "5ATM/50 meters", "железные 22mm",100, "Часы из нержавеющей стали", true, images3);
//
//        itemRepository.save(item3);
//
//        //--------------------------------------------------------------------------------------------------------------
//        List<Image> images4 = new ArrayList<>(3);
//        byte[] img4_1 = Files.readAllBytes(ResourceUtils.getFile("classpath:static/images/item4-1.png").toPath());
//        images4.add(new Image(null, img4_1, true));
//
//        byte[] img4_2 = Files.readAllBytes(ResourceUtils.getFile("classpath:static/images/item4-2.png").toPath());
//        images4.add(new Image(null, img4_2, false));
//
//        Item item4 = new Item("Volumenzeit S 6", BigDecimal.valueOf(20,588), 120, 5d, Color.GREEN, "Сапфир Кристал" ,
//                "5ATM/50 meters", "железные 22mm",100, "Часы из нержавеющей стали", true, images4);
//
//        itemRepository.save(item4);
//
//        //--------------------------------------------------------------------------------------------------------------
//        List<Image> images5 = new ArrayList<>(3);
//        byte[] img5_1 = Files.readAllBytes(ResourceUtils.getFile("classpath:static/images/item5-1.png").toPath());
//        images5.add(new Image(null, img5_1, true));
//
//        byte[] img5_2 = Files.readAllBytes(ResourceUtils.getFile("classpath:static/images/item3-2.jpg").toPath());
//        images5.add(new Image(null, img5_2, false));
//
//        Item item5 = new Item("Molumenzeit S 2", BigDecimal.valueOf(17,588), 120, 5d, Color.BLUE, "Сапфир Кристал" ,
//                "10ATM/50 meters", "железные 22mm",100, "Часы из нержавеющей стали", true, images5);
//
//        itemRepository.save(item5);
    }
}
