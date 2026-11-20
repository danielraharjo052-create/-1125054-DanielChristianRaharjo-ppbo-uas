package nusazoo.view;

import nusazoo.model.InvalidInputException;
import nusazoo.model.TicketType;
import nusazoo.model.Tiket;

public class TiketDataView extends DataView<Tiket> {
    public TiketDataView(ConsoleIO io) {
        super(io);
    }

    @Override protected String getPrimaryKeyValue(Tiket d) { return d.getKodeTiket(); }

    @Override
    protected String[] getFields() {
        return new String[]{"kodeTiket", "idPengunjung", "jenis(REGULER/ANAK/VIP/DONASI_ADOPSI)", "harga"};
    }

    @Override
    protected Tiket convertValuesToModel(String[] v) {
        try {
            return new Tiket(v[0], v[1], TicketType.valueOf(v[2].trim().toUpperCase()), Double.parseDouble(v[3]));
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Harga harus berupa angka");
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("Jenis tiket tidak dikenal");
        }
    }

    @Override
    protected String[] convertModelToValues(Tiket d) {
        return new String[]{d.getKodeTiket(), d.getIdPengunjung(), d.getJenis().name(), String.valueOf(d.getHarga())};
    }
}
