package de.stekoe.idss.page.project.criterion.referencecatalog;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.model.MeasurementValue;
import de.stekoe.idss.model.NominalScaledCriterion;
import de.stekoe.idss.model.OrdinalScaledCriterion;
import de.stekoe.idss.model.SingleScaledCriterion;

public class ReferenceCriterionExporter {
    private List<Criterion> criterions;
    private List<CriterionGroup> criterionGroups;

    public void setCriterions(List<Criterion> criterions) {
        this.criterions = criterions;
    }

    public void setCriterionGroups(List<CriterionGroup> criterionGroups) {
        this.criterionGroups = criterionGroups;
    }

    public File createXml() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // criteriaCatalog
            Element rootElement = doc.createElement("criteriaCatalog");
            doc.appendChild(rootElement);

            createCriterionElements(doc, rootElement);
            createCriterionGroupElements(doc, rootElement);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            File tmpFile = File.createTempFile("referenceCriterionCatalog", ".export");
            transformer.transform(source, new StreamResult(tmpFile));

            return tmpFile;
        } catch(Exception e) {

        }

        return null;
    }

    private void createCriterionGroupElements(Document doc, Element rootElement) {
        Element criterionGroups = doc.createElement("criterionGroups");
        rootElement.appendChild(criterionGroups);

        for(CriterionGroup cg : this.criterionGroups) {
            Element criterionGroup = doc.createElement("criterionGroup");
            criterionGroups.appendChild(criterionGroup);

            // id attribute
            criterionGroup.setAttribute("id", cg.getId());

            Element name = doc.createElement("name");
            name.setTextContent(cg.getName());
            criterionGroup.appendChild(name);

            Element description = doc.createElement("description");
            description.setTextContent(cg.getDescription());
            criterionGroup.appendChild(description);

            Element criterions = doc.createElement("criterions");
            criterionGroup.appendChild(criterions);

            for(Criterion c : cg.getCriterions()) {
                Element criterion = doc.createElement("criterion");
                criterion.setAttribute("refid", c.getId());
                criterions.appendChild(criterion);
            }
        }
    }

    private void createCriterionElements(Document doc, Element rootElement) {
        // criterions
        Element criterions = doc.createElement("criterions");
        rootElement.appendChild(criterions);

        for(Criterion c : this.criterions) {
            Element criterion = doc.createElement("criterion");
            criterions.appendChild(criterion);

            // id attribute
            criterion.setAttribute("id", c.getId());

            // type attribute
            if(c instanceof NominalScaledCriterion) {
                criterion.setAttribute("type", "NOMINAL");
            } else if(c instanceof OrdinalScaledCriterion) {
                criterion.setAttribute("type", "ORDINAL");
            } else {
                continue;
            }

            // Name
            Element name = doc.createElement("name");
            name.setTextContent(c.getName());
            criterion.appendChild(name);

            // Description
            Element description = doc.createElement("description");
            description.setTextContent(c.getDescription());
            criterion.appendChild(description);

            // values
            Element values = doc.createElement("values");
            criterion.appendChild(values);

            if(c instanceof SingleScaledCriterion) {
                SingleScaledCriterion<MeasurementValue> ssc = (SingleScaledCriterion<MeasurementValue>) c;
                for(MeasurementValue mv : ssc.getValues()) {
                    Element value = doc.createElement("value");
                    value.setAttribute("id", mv.getId());
                    value.setTextContent(mv.getValue());
                    values.appendChild(value);
                }
            }
        }
    }
}
