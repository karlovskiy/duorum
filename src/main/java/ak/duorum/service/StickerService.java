package ak.duorum.service;

import ak.duorum.entity.StickerStatus;
import ak.duorum.entity.Unit;
import ak.duorum.entity.UnitSticker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Here will be javadoc
 *
 * @author karlovskiy
 * @since 1.0, 6/26/14
 */
@Service("stickerService")
@Transactional
public class StickerService {

    private static final Logger logger = LoggerFactory.getLogger(StickerService.class);

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public Unit removeSticker(Long id) {
        logger.info("Start removing sticker[unit id={}]", id);
        Unit unit = (Unit) sessionFactory.getCurrentSession().get(Unit.class, id);
        unit.setStickerStatus(StickerStatus.DEFAULT);
        logger.info("End removing sticker[unit id={}]", id);
        return unit;
    }

    public long removeAllStickers() {
        logger.info("Start removing stickers for all units.");
        Session session = sessionFactory.getCurrentSession();
        Long exists = (Long) session.createSQLQuery(
                "SELECT COUNT(u.id) as stickers from UNITS u where u.sticker_status = 1")
                .addScalar("stickers", StandardBasicTypes.LONG)
                .uniqueResult();
        if(0 != exists){
            sessionFactory.getCurrentSession()
                    .createSQLQuery("UPDATE UNITS SET sticker_status = 0")
                    .executeUpdate();
        }

        logger.info("End removing stickers for all units, {} removed", exists);
        return exists;
    }

    public List<UnitSticker> loadStickers() {
        logger.info("Start loading stickers.");
        Session session = sessionFactory.getCurrentSession();
        @SuppressWarnings("unchecked")
        List<UnitSticker> stickers = session.createCriteria(UnitSticker.class)
                .addOrder(Order.asc("designation"))
                .list();
        logger.info("End loading stickers, {} loaded.", stickers.size());
        return stickers;
    }

}
