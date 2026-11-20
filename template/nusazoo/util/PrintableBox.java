package nusazoo.util;

import nusazoo.model.Printable;

public class PrintableBox<T extends Printable> extends Box<T> implements Printable {
    public PrintableBox(T content) {
        super(content);
    }

    @Override
    public String getPrintDetail() {
        return getContent() == null ? "(empty)" : getContent().getPrintDetail();
    }
}
