package nusazoo.view;

import nusazoo.model.HabitatType;
import nusazoo.model.InvalidInputException;
import nusazoo.model.Kandang;

public class KandangDataView extends DataView<Kandang> {
    public KandangDataView(ConsoleIO io) {
        super(io);
    }

    @Override protected String getPrimaryKeyValue(Kandang d) { return d.getKodeKandang(); }

    @Override protected String[] getFields() { return new String[]{"kodeKandang", "nama", "kapasitas", "habitat(LAND/SEA/AIR)"}; }

    @Override
    protected Kandang convertValuesToModel(String[] v) {
        try {
            return new Kandang(v[0], v[1], Integer.parseInt(v[2]), HabitatType.valueOf(v[3].trim().toUpperCase()));
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Kapasitas harus berupa angka");
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("Habitat harus LAND, SEA, atau AIR");
        }
    }

    @Override
    protected String[] convertModelToValues(Kandang d) {
        return new String[]{d.getKodeKandang(), d.getNama(), String.valueOf(d.getKapasitas()), d.getHabitat().name()};
    }
}
