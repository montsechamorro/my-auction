import java.util.ArrayList;

/**
 * A simple model of an auction.
 * The auction maintains a list of lots of arbitrary length.
 *
 * @author David J. Barnes and Michael KÃ¶lling.
 * @version 2011.07.31
 */
public class Auction
{
    // The list of Lots in this auction.
    private ArrayList<Lot> lots;
    // The number that will be given to the next lot entered
    // into this auction.
    private int nextLotNumber;

    /**
     * Create a new auction.
     */
    public Auction()
    {
        lots = new ArrayList<Lot>();
        nextLotNumber = 1;
    }

    /**
     * Enter a new lot into the auction.
     * @param description A description of the lot.
     */
    public void enterLot(String description)
    {
        lots.add(new Lot(nextLotNumber, description));
        nextLotNumber++;
    }

    /**
     * Show the full list of lots in this auction.
     */
    public void showLots()
    {              
        for(Lot lot : lots){ 
            System.out.println(lot.toString());
        }

    }

    /**
     * Make a bid for a lot.
     * A message is printed indicating whether the bid is
     * successful or not.
     * 
     * @param lotNumber The lot being bid for.
     * @param bidder The person bidding for the lot.
     * @param value  The value of the bid.
     */
    public void makeABid(int lotNumber, Person bidder, long value)
    {
        Lot selectedLot = getLot(lotNumber);
        if(selectedLot != null) {

            boolean successful = selectedLot.bidFor(new Bid(bidder, value));
            if(successful) {
                System.out.println("The bid for lot number " +
                    lotNumber + " was successful.");
            }
            else {
                // Report which bid is higher.
                Bid highestBid = selectedLot.getHighestBid();
                System.out.println("Lot number: " + lotNumber +
                    " already has a bid of: " +
                    highestBid.getValue());
            }
        }
    }

    /**
     * Return the lot with the given number. Return null
     * if a lot with this number does not exist.
     * @param lotNumber The number of the lot to return.
     */
    public Lot getLot(int lotNumber)
    {
        if((lotNumber >= 1) && (lotNumber < nextLotNumber)) {
            // The number seems to be reasonable.
            Lot selectedLot = lots.get(lotNumber - 1);
            // Include a confidence check to be sure we have the
            // right lot.
            if(selectedLot.getNumber() != lotNumber) {
                System.out.println("Internal error: Lot number " +
                    selectedLot.getNumber() +
                    " was returned instead of " +
                    lotNumber);
                // Don't return an invalid lot.
                selectedLot = null;
            }
            return selectedLot;
        }
        else {
            System.out.println("Lot number: " + lotNumber +
                " does not exist.");
            return null;
        }
    }

    /**
     * metodo que muestra todos los detalles de los items que se estan subastando
     * de los que ha habido pujas, muestra el nombre de la persona que ha hecho la puja mas alta
     * y el valor de dicha puja. del resto debe indicar que no ha habido pujas
     */
    public void close()
    {

        for(Lot lot : lots) {
            String details = lot.getNumber() + " : " + lot.getDescription();

            if (lot.getHighestBid() != null)
            {
                details = details + "(oferta más alta: )" + lot.getHighestBid().getValue() + " de: " + lot.getHighestBid().getBidder().getName();
                                                          //esto nos genera un objeto bid sobre el que invocamos getValue
                                                          //al invocar getBidder nos devuelve un objto person al que invocamos getname
            }
            else
            {
                details = details + "(Todavía no hay oferta)";
            }
            System.out.println(details);
        }

    }
    /**
     * metodo que devuelve una colección de los todos los items por los que no habido ninguna puja
     * y no imprima nada por pantalla
     */
    public ArrayList getUnsold()
    {
        ArrayList<Lot> unsold = new ArrayList<Lot>();
        for (Lot lote : lots)
        {
            if(lote.getHighestBid() == null)
            {
                unsold.add(lote);
            }
        }
        return unsold;
    }
}

