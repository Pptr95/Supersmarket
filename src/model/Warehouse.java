package model;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import model.discountstrategies.DiscountStrategy;
import model.modifylists.ModifyList;

/**
 * Model implementation representing a warehouse in which lots are stored.
 * Can also perform other actions as described by the interface
 */

public class Warehouse implements Model {

    private List<LotWithActions> lots;

    /**
     * Default constructor that initializes the internal list.
     */
    public Warehouse() {
        this.lots = new ArrayList<>();
    }

    @Override
    public void initialize(final Optional<ObjectInputStream> serializedModel) {
        if (!serializedModel.isPresent()) {
            LotBuilder.setNextId(0);
        } else {
            final ObjectInputStream buffer = serializedModel.get();
            try {
                LotBuilder.setNextId(buffer.readInt());
                Object obj = null;
                while ((obj = buffer.readObject()) != null) {
                    if (obj instanceof LotWithActions) {
                        lots.add((LotWithActions) obj);
                    }
                }
            } catch (EOFException e) { //File has reached its end, we read it all.
            }
            catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void serializeModel(final ObjectOutputStream output) {
        try {
            output.writeInt(LotBuilder.getNextId());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        getList(null).forEach(l -> {
            try {
                output.writeObject(l);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public List<Lot> getList(final ModifyList mfl) {
        final List<Lot> toReturn = new ArrayList<Lot>();
        this.lots.forEach(l -> toReturn.add(l.getLot()));
        if (mfl != null) {
            return mfl.modify(toReturn);
        }
        return toReturn;
    }

    @Override
    public void addLotto(final Lot lot) {
        this.lots.add(new LotImpl(lot));
    }

    @Override
    public void removeFromLot(final int id, final int n) {
        this.lots.forEach(l -> {
            if (l.getId() == id) {
                l.removeElements(n);
            }
        });
        this.lots = this.lots.stream().filter(l -> l.getCurrentQuantity() > 0).collect(Collectors.toList());

    }

    @Override
    public Map<Lot, Integer> getDiscountable(final DiscountStrategy ds) {
        return ds.suggestDiscounts(this.getList(null));
    }

    @Override
    public void setOnSale(final int id, final int discountAmount) {
        this.lots.forEach(l -> {
            if (l.getId() == id) {
                l.setOnSale(discountAmount);
            }
        });
    }
}
