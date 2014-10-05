package ak.duorum.service;

import ak.duorum.controller.ImportResultType;
import ak.duorum.entity.Unit;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Here will be javadoc
 *
 * @author karlovskiy
 * @since 1.0, 6/17/14
 */
@Service("unitService")
@Transactional
public class UnitService {

    private static final Logger logger = LoggerFactory.getLogger(UnitService.class);

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public Long createUnit(Unit unit) {
        logger.info("Saving new unit.");
        Session session = sessionFactory.getCurrentSession();
        return (Long) session.save(unit);
    }

    public void updateUnit(Unit unit) {
        logger.info("Updating unit with id=" + unit.getId());
        Session session = sessionFactory.getCurrentSession();
        Unit oldUnit = (Unit) session.get(Unit.class, unit.getId());
        oldUnit.setDesignation(unit.getDesignation());
        oldUnit.setCode(unit.getCode());
        oldUnit.setName(unit.getName());
        oldUnit.setDescription(unit.getDescription());
        oldUnit.setLabelName(unit.getLabelName());
        oldUnit.setLabelDescription(unit.getLabelDescription());
        oldUnit.setLot(unit.getLot());
        oldUnit.setSerialNumber(unit.getSerialNumber());
        String newLanguage = unit.getLanguage();
        if (StringUtils.isNotEmpty(newLanguage)) {
            oldUnit.setLanguage(unit.getLanguage());
        } else {
            oldUnit.setLanguage("00");
        }
    }

    public List<Unit> loadLastCreated(int count) {
        logger.debug("Start loading last {} created units.", count);
        @SuppressWarnings("unchecked")
        List<Unit> units = sessionFactory.getCurrentSession().createCriteria(Unit.class)
                .addOrder(Order.desc("creationDate"))
                .setMaxResults(count)
                .list();
        logger.debug("End loading last {} created units, {} loaded.", units.size());
        return units;
    }

    public List<Unit> loadLastModified(int count) {
        logger.debug("Start loading last {} modified units.", count);
        @SuppressWarnings("unchecked")
        List<Unit> units = sessionFactory.getCurrentSession().createCriteria(Unit.class)
                .add(Restrictions.isNotNull("lastModificationDate"))
                .addOrder(Order.desc("lastModificationDate"))
                .setMaxResults(count)
                .list();
        logger.debug("End loading last {} modified units, {} loaded.", units.size());
        return units;
    }

    public List<Unit> loadUnits(String search) {
        logger.debug("Start loading units.");
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Unit.class);
        if (StringUtils.isNotEmpty(search)) {
            criteria.add(Restrictions.ilike("designation", search, MatchMode.ANYWHERE));
        }
        @SuppressWarnings("unchecked")
        List<Unit> units = criteria.addOrder(Order.asc("designation")).list();
        logger.debug("End loading units, {} loaded.", units.size());
        return units;
    }

    public Unit loadUnit(Long id) {
        logger.info("Loading unit with id=" + id);
        Session session = sessionFactory.getCurrentSession();
        return (Unit) session.get(Unit.class, id);
    }

    public Unit loadUnit(String designation) {
        logger.info("Loading unit with designation=" + designation);
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Unit.class);
        criteria.add(Restrictions.eq("designation", designation));
        return (Unit) criteria.uniqueResult();
    }

    public void printSticker(Long id) {
        logger.info("Queue sticker for print[unit id={}]", id);
        sessionFactory.getCurrentSession()
                .createSQLQuery("UPDATE UNITS SET sticker_status = 1 WHERE id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    public ImportResultType<Unit> importFile(InputStream is) throws IOException, InvalidFormatException {
        ImportResultType<Unit> result = new ImportResultType<Unit>();
        try {
            Workbook wb = WorkbookFactory.create(is);
            Sheet sheet = wb.getSheetAt(0);
            int i = 0;
            for (Row row : sheet) {
                i++;
                if (i > 1) {
                    Unit unit = makeUnit(row);
                    if (StringUtils.isEmpty(unit.getDesignation())) {
                        continue;
                    }
                    Session session = sessionFactory.getCurrentSession();
                    Unit oldUnit = (Unit) session.createCriteria(Unit.class)
                            .add(Restrictions.eq("designation", unit.getDesignation()))
                            .uniqueResult();
                    if (oldUnit != null) {
                        result.addExists(oldUnit);
                    } else {
                        Long id = (Long) session.save(unit);
                        unit.setId(id);
                        result.addAdded(unit);
                    }
                }
            }
        } finally {
            is.close();
        }
        return result;
    }

    private Unit makeUnit(Row row) {
        Unit unit = new Unit();
        for (int j = 0; j < 9; j++) {
            Cell cell = row.getCell(j, Row.CREATE_NULL_AS_BLANK);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            String value = cell.getStringCellValue();
            if (value != null) {
                value = value.trim();
                if (value.equals("-") || value.equals("")) {
                    value = null;
                }
            }
            switch (j) {
                case 0:
                    unit.setDesignation(value);
                    break;
                case 1:
                    unit.setCode(value);
                    break;
                case 2:
                    unit.setName(value);
                    break;
                case 3:
                    unit.setDescription(value);
                    break;
                case 4:
                    unit.setLabelName(value);
                    break;
                case 5:
                    unit.setLabelDescription(value);
                    break;
                case 6:
                    if ("ENG".equalsIgnoreCase(value)) {
                        value = "02";
                    } else if ("RU".equalsIgnoreCase(value)) {
                        value = "01";
                    }
                    unit.setLanguage(value);
                    break;
                case 7:
                    if (value != null && value.length() == 4) {
                        unit.setLot(value);
                    }
                    break;
                case 8:
                    unit.setSerialNumber(value);
            }
        }
        logger.debug("Parsed unit {}", unit);
        return unit;
    }

}
