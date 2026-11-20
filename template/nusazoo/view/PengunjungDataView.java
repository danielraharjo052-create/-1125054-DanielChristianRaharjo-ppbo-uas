package nusazoo.view;

import nusazoo.model.InvalidInputException;
import nusazoo.model.Pengunjung;

public class PengunjungDataView extends DataView<Pengunjung> {
    public PengunjungDataView(ConsoleIO io) {
        super(io);
    }

    @Override protected String getPrimaryKeyValue(Pengunjung d) { return d.getIdPengunjung(); }

    @Override protected String[] getFields() { return new String[]{"idPengunjung", "nama", "email", "umur"}; }

    @Override
    protected Pengunjung convertValuesToModel(String[] v) {
        try {
            return new Pengunjung(v[0], v[1], v[2], Integer.parseInt(v[3]));
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Umur harus berupa angka");
        }
    }

    @Override
    protected String[] convertModelToValues(Pengunjung d) {
        return new String[]{d.getIdPengunjung(), d.getNama(), d.getEmail(), String.valueOf(d.getUmur())};
    }
}
