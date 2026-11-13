/**
 * Makes a menu of all hotdog types for sale
 * Also lists the price of each type of hotdog
 * Hotdogs have a base price
 * but combinations and stuff added on to the glizzy will increase the price
 *
 * @author Dylan Strohbeck
 * @version 1.0
 */
public class HotDog {

   /** Price of a normal glizzy on a bun */
   private static final int BASE_PRICE = 99;

   /**Different types of toppings  */
   private static final String[] TOPPINGS = {
      "Ketchup", "Mustard", "Chopped Onions", "Tomato Wedge",
      "Sweet Relish", "Sport Peppers", "Sauerkraut"
   };

   /** All of the prices for each topping */
   private static final int[] PRICES = { 15, 17, 20, 25, 30, 35, 45 };

   /** Width of description column */
   private static final int DESCRIPTION_WIDTH = 85;

   /** Menu prices printed so far */
   private long count;

   /**
    * Every possible combination of bun
    * 
    *
    * @return the number of combinations printed
    */
   public long printMenu( ) {
      // reset counter so menu can be printed multiple times
      count = 0;

      // 0 = No, 1 = Yes for each topping; start with no toppings
      int[ ] selected = new int[ TOPPINGS.length ];

      // print the plain dog (the weight watcher special)
      printItem( selected );

      // print every combination that has at least one topping
      addToppings( selected, 0 );

      return count;
   }

   /**
    * Prints every combination that can be made by adding toppings at
    * index start or later to the toppings already selected. First every
    * way to add one more topping is printed, then each of those
    * combinations is expanded further.
    *
    * @param selected 0/1 flags showing which toppings are already chosen
    * @param start    the first topping index that may still be added
    */
   private void addToppings( int[ ] selected, int start ) {
      // print each combination made by adding exactly one more topping
      for ( int i = start; i < TOPPINGS.length; i++ ) {
         // turn the topping on, print the item, then turn it back off
         selected[ i ] = 1;
         printItem( selected );
         selected[ i ] = 0;
      }

      // expand each of those combinations with the toppings after it
      for ( int i = start; i < TOPPINGS.length; i++ ) {
         // turn the topping on, build on it, then turn it back off
         selected[ i ] = 1;
         addToppings( selected, i + 1 );
         selected[ i ] = 0;
      }
   }

   /**
    * Prints one numbered line of the menu showing the description and
    * price of the combination described by selected, then adds one to
    * the menu item count.
    *
    * @param selected 0/1 flags showing which toppings are chosen
    */
   private void printItem( int[ ] selected ) {
      // start the description and price with the plain dog in a bun
      StringBuilder description = new StringBuilder( "Dog in Bun" );
      int price = BASE_PRICE;
      boolean firstTopping = true;

      // add the name and price of each chosen topping
      for ( int i = 0; i < TOPPINGS.length; i++ ) {
         // only toppings flagged with a 1 (Yes) are included
         if ( selected[ i ] == 1 ) {
            // the first topping gets "with", the rest get a comma
            if ( firstTopping ) {
               description.append( " with " );
               firstTopping = false;
            } else {
               description.append( ", " );
            }
            description.append( TOPPINGS[ i ] );
            price += PRICES[ i ];
         }
      }

      // print the item number, description, and price in dollars
      System.out.printf( "%3d %-" + DESCRIPTION_WIDTH + "s $%d.%02d%n",
            count, description, price / 100, price % 100 );

      // count this menu item
      count++;
   }

   /**
    * Prints the full menu and checks that all 128 combinations were made.
    *
    * @param args command-line arguments (not used)
    */
   public static void main( String [ ] args ) {
      // create the menu printer and print the menu
      HotDog obj = new HotDog( );
      long count = obj.printMenu( );

      // 7 toppings, each Yes or No, gives 2^7 = 128 combinations
      assert count == 128;
   }
}