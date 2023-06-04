package org.jasa.jreport.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jasa.jreport.util.Processor;

import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

/**
 * Main class for report generation. This class relies on a {@link Model} as
 * input data, which will be processed to generate a PDF report based on an ODT
 * template with {@code Freemarker} features.
 * 
 * You can visit the official documentation of the Freemarker project at
 * https://freemarker.apache.org/
 * 
 * @author Leonardo Sanchez J.
 */
public class JReport {

    private static final Logger logger = Logger.getLogger(JReport.class.getName());

    private File file;
    private OutputStream outputStream;

    /**
     * Generate a report based on an established {@link Model}. Note: The
     * {@link JReport#getFile()} and {@link JReport#getOutputStream()} methods may
     * be of interest to you."
     * 
     * @param model
     * @return
     */
    public JReport generate(Model model) {
        try {

            logger.info("Generating JReport...");

            // 1) Load ODT file and set Velocity template engine and cache it to the
            // registry
            InputStream in = new FileInputStream(model.getInFile());
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Freemarker);

            // 2) Create Java model context
            IContext context = report.createContext();
            FieldsMetadata fieldsMetadata = report.createFieldsMetadata();

            // 3) Load params
            Processor.valueData(context, model);

            // 4) Load list data
            Processor.dataList(context, fieldsMetadata, report, model);

            // 5) Load query data
            Processor.dataQuery(context, fieldsMetadata, report, model);

            // 6) Set PDF as format converter
            Options options = Options.getTo(ConverterTypeTo.PDF);

            // 7) Generate report by merging Java model with the ODT and convert it to PDF
            OutputStream out = new FileOutputStream(model.getOutFile());

            try {
                final Logger app = Logger.getLogger("org.odftoolkit.odfdom.pkg.OdfXMLFactory");
                app.setLevel(Level.WARNING);
            } catch (Exception e) {
            }
            report.convert(context, options, out);
            this.file = model.getOutFile();
            this.outputStream = out;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        logger.info("JReport generated!");
        return this;
    }

    /**
     * Obtain the {@link File} representation of the generated file. It can be used
     * as follows: {@code File file = new JReport(model).getFile();}
     * 
     * @return {@link File} representation
     */
    public File getFile() {
        return this.file;
    }

    /**
     * Obtain the {@link OutputStream} representation of the generated file. It can
     * be used as follows: {@code File file = new JReport(model).getOutputStream();}
     * 
     * @return {@link OutputStream} representation
     */
    public OutputStream getOutputStream() {
        return this.outputStream;
    }
}
