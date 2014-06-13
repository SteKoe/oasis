package de.stekoe.idss;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.model.NominalScaledCriterion;
import de.stekoe.idss.model.NominalValue;
import de.stekoe.idss.model.OrdinalScaledCriterion;
import de.stekoe.idss.model.OrdinalValue;

public class XmlImport {
    private static final Logger LOG = Logger.getLogger(XmlImport.class);

    private final List<CriterionGroup> criterionGroups = new ArrayList<CriterionGroup>();
    private final Map<String, Criterion> criterions = new HashMap<String, Criterion>();

    private Document document;

    public XmlImport(File xmlFile) throws DocumentException {
        if(xmlFile != null) {
            SAXReader reader = new SAXReader();
            document = reader.read(xmlFile);

            processCriterions();
            processGroups();
        }
    }

    private void processCriterions() {
        List<Element> criterions = document.selectNodes("//criterions/criterion");
        for (Element criterion : criterions) {
            Attribute idAttribute = criterion.attribute("id");
            if(idAttribute != null) {
                Criterion crit = processCriterion(criterion);
                this.criterions.put(idAttribute.getText(), crit);
            }
        }
    }

    private Criterion processCriterion(Element criterion) {
        Attribute criterionType = criterion.attribute("type");
        Attribute idNode = criterion.attribute("id");
        Node nameNode = criterion.selectSingleNode("name");
        Node descriptionNode = criterion.selectSingleNode("description");
        List<Element> values = criterion.selectNodes("values/value");

        if("NOMINAL".equals(criterionType.getText())) {
            NominalScaledCriterion crit = new NominalScaledCriterion();
            crit.setReferenceType(true);

            if(idNode != null && isValidUUID(idNode.getText())) crit.setId(idNode.getText());
            if(nameNode != null) crit.setName(nameNode.getText());
            if(descriptionNode != null) crit.setDescription(nameNode.getText());

            for (Element element : values) {
                NominalValue val = new NominalValue(element.getText());

                Attribute valueIdAttribute = element.attribute("id");
                if(valueIdAttribute != null && isValidUUID(valueIdAttribute.getText())) {
                    val.setId(valueIdAttribute.getText());
                }
                crit.getValues().add(val);
                val.setCriterion(crit);
            }

            return crit;
        } else if("ORDINAL".equals(criterionType.getText())) {
            OrdinalScaledCriterion crit = new OrdinalScaledCriterion();
            crit.setReferenceType(true);

            if(idNode != null && isValidUUID(idNode.getText())) crit.setId(idNode.getText());
            if(nameNode != null) crit.setName(nameNode.getText());
            if(descriptionNode != null) crit.setDescription(nameNode.getText());

            for (Element element : values) {
                OrdinalValue val = new OrdinalValue(element.getText());

                Attribute valueIdAttribute = element.attribute("id");
                if(valueIdAttribute != null && isValidUUID(valueIdAttribute.getText())) {
                    val.setId(valueIdAttribute.getText());
                }

                crit.getValues().add(val);
                val.setCriterion(crit);
            }

            return crit;
        }

        return null;
    }

    private void processGroups() {
        List<Element> criterionGroups = document.selectNodes("//criterionGroups/criterionGroup");
        for (Element criterionGroup : criterionGroups) {
            CriterionGroup cg = new CriterionGroup();
            cg.setReferenceType(true);

            // Id
            Attribute idAttr = criterionGroup.attribute("id");
            if(idAttr != null) {
                String uuid = idAttr.getText();
                if(isValidUUID(uuid)) {
                    cg.setId(uuid);
                }
            }

            // Name
            Node nameNode = criterionGroup.selectSingleNode("name");
            if(nameNode != null) {
                cg.setName(nameNode.getText());
            }

            // Description
            Node descriptionNode = criterionGroup.selectSingleNode("description");
            if(descriptionNode != null) {
                cg.setDescription(descriptionNode.getText());
            }

            // Values
            List<Element> referenceCriterions = criterionGroup.selectNodes("criterions/criterion");
            for (Element referencedCriterion : referenceCriterions) {
                Attribute refid = referencedCriterion.attribute("refid");
                Criterion crit = criterions.get(refid.getText());
                if(crit != null) {
                    criterions.remove(refid);
                    crit.getCriterionGroups().add(cg);
                    cg.getCriterions().add(crit);
                }
            }

            getCriterionGroups().add(cg);
        }
    }

    boolean isValidUUID(String uuid) {
        return uuid.matches("[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}");
    }

    /**
     * This method returns all criterions which are not part of any group.
     *
     * @return a list of Criterions
     */
    public List<Criterion> getCriterions() {
        List<Criterion> criterions = new ArrayList<Criterion>();
        for (Entry<String, Criterion> entry : this.criterions.entrySet()) {
            Criterion value = entry.getValue();
            if(value != null) {
                criterions.add(value);
            }
        }
        return criterions;
    }

    /**
     * This method returns all CriterionGroups found in the imported XML file including the references to Criterions.
     *
     * @return a list of CriterionGroups
     */
    public List<CriterionGroup> getCriterionGroups() {
        return criterionGroups;
    }
}