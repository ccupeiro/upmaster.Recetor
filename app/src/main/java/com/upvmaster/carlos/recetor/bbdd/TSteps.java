package com.upvmaster.carlos.recetor.bbdd;

/**
 * Created by Carlos on 07/12/2016.
 */

public class TSteps {
    public static final String TABLE_NAME = "steps";

    public static final String ID = "id";
    public static final String NUM = "num";
    public static final String DESCRIPTION = "description";
    public static final String RECEIPT_ID = "receiptId";

    public static final String CREATE_TABLE = "CREATE TABLE '"+TABLE_NAME+"' ("+
            "`"+ID+"`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"+
            "`"+RECEIPT_ID+"`	INTEGER,"+
            "`"+NUM+"` INTEGER,"+
            "`"+DESCRIPTION+"`	TEXT)";

    public static final String INSERT ="INSERT INTO "+TABLE_NAME
            +" ("+RECEIPT_ID+","
            + NUM+","
            +DESCRIPTION+")"
            +" VALUES (?,?,?)";
    public static final String UPDATE ="UPDATE "+TABLE_NAME
            +" SET "+RECEIPT_ID+"= ? ,"
            + NUM +"= ? ,"
            + DESCRIPTION + "= ? "
            +"WHERE "+ID+"= ?";
    public static final String DELETE ="DELETE FROM "+TABLE_NAME
            +" WHERE "+ID+"=?";

    public static final String DELETE_BY_RECEIPT ="DELETE FROM "+TABLE_NAME
            +" WHERE "+RECEIPT_ID+"=?";

    public static final String SELECT_FROM_RECEIPT="SELECT * FROM "+TABLE_NAME+" WHERE "+RECEIPT_ID+"= ? ORDER BY "+ID;

    public static  final String INSERT_EX = "INSERT INTO '"+TABLE_NAME+"' ("+ID+","+RECEIPT_ID+","+NUM+","+DESCRIPTION+") VALUES "+
            "(1,1,1,'Pon en una cacerola a cocerse las patatas y la zanahoria, todo bien troceado en daditos o en rodajas para que tarden menos en hacerse. El huevo lo pones a cocerse en otra cacerola con agua para que se ponga duro.'),"+
            "(2,1,2,'Cuando todo esté bien cocido, pela el huevo y lo troceas en trocitos tan pequeños como puedas y las rodajas de la zanahoria y la patata en daditos si no lo hiciste antes. Todo esto lo pones en una fuente, removiendo bien.'),"+
            "(3,1,3,'Agrega el atún, sin el aceite, y remueve de nuevo para se vayan alternando entre ellos.'),"+
            "(4,1,4,'Coloca las aceitunas, repartidas por la fuente y los guisantes en conserva.'),"+
            "(5,1,5,'Por último vierte la mayonesa y remueve bien, para mezclar todos los ingredientes con la salsa.'),"+
            "(6,1,6,'Deja reposar un poco en la nevera y tendrás listo este plato para disfrutarlo.'),"+
            "(7,2,1,'Pelamos bien la cebolla y la lavamos un poco, para reducir la cantidad de efecto lacrimógeno que tiene. La troceamos en trocitos muy pequeños o directamente la pasamos por la trituradora, como vosotros prefiráis. Una vez lista, la ponemos en la sartén con un buen chorro de aceite.'),"+
            "(8,2,2,'Nos iremos ocupando de pelear y limpiar bien las otras verduras que vayamos a utilizar. El calabacín recomendamos cortarlo por la mitad si es muy grande antes de pelarlo, para que sea más manejable. En el caso de los pimientos, vamos a cortarlos en tiras, a lo largo, y luego los troceamos. Cortarlo todo en trocitos muy pequeños, como daditos.'),"+
            "(9,2,3,'Con la cebolla ya dorada, echamos poco a poco cada verdura. Aseguraos de dejar un margen de tiempo de uno dos minutos entre verdura y verdura, no dejando de remover para que quede todo bien mezclado.'),"+
            "(10,2,4,'Vamos pelando y limpiando bien las patatas, cortándola también en pequeños dados. Cuando las verduras empiecen a estar doradas, echamos el tomate frito y lo removemos todo para que se mezcle bien.'),"+
            "(11,2,5,'Pasados unos cinco minutos añadimos las patatas y lo rehogamos todo en la salsa. Ahora es cuando agregaremos sal al gusto, junto con la cucharada de pimentón.'),"+
            "(12,3,1,'Para empezar, pon agua a calentar en una olla para cocer el arroz. Cuando el agua esté hirviendo echa el arroz con una gotitas de aceite, remuévelo y déjalo cocer durante 20 minutos.'),"+
            "(13,3,2,'Una vez tengas el arroz cocido (pruébalo para asegurarte de que no está duro ni pasado) escúrrelo con un colador y resérvalo (en este punto te recomendamos que enfríes el arroz bajo el agua del grifo para parar su cocción).'),"+
            "(14,3,3,'A continuación pon un chorrito de aceite a calentar en una sartén y saltea los ajos cortados previamente en láminas finas. Cuando los ajos estén dorados, incorpora el arroz que tenías reservado, sálalo al gusto y remuévelo para integrar ambos ingredientes.'),"+
            "(15,3,4,'En otra sartén, pon a calentar la mantequilla. Una vez la mantequilla esté derretida incorpora los plátanos previamente pelados y cortados a los largo por la mitad. Fríe los plátanos a fuego medio hasta que queden blandos por dentro y dorados por fuera.'),"+
            "(16,3,5,'Con el arroz y los plátanos ya preparados, solo quedará que frías los huevos en abundante aceite y en otro recipiente calientes el tomate frito (puedes hacerlo en el fuego o en el microondas).'),"+
            "(17,3,6,'Con todos los ingredientes de tu receta preparados ahora solo queda emplatar. Pon en cada plato, un cucharón generoso del arroz salteado con los ajos, con la salsa de tomate frito caliente encima. A un lado pon un huevo y un plátano frito y ya está listo el arroz a la cubana.'),"+
            "(18,4,1,'Pela la cebolla y la picas en trocitos bien pequeños. Si ves que lo ojos te lloran mucho la lavas para reducir su efecto lacrimógeno. La sofríes en una sartén con aceite junto con los dientes de ajo.'),"+
            "(19,4,2,'Cuando estén dorados, agrega un chorrito de vino blanco y sube la potencia del fuego para que se reduzca más rápidamente.'),"+
            "(20,4,3,'Agrega entonces la carne picada y sofríe todo junto, hasta que la carne esté bien dorada.'),"+
            "(21,4,4,'Unta mantequilla o aceite de oliva en una bandeja que sirva para hornos y pon la primera capa de pasta de lasaña. Cubre con una capa de carne y recubre con una masa.'),"+
            "(22,4,5,'Pon una capa de bechamel y recubre… y así tantas veces como quieras.'),"+
            "(23,4,6,'Esparce por encima tanto queso rallado como tú quieras, el tiempo que quieras y al horno a 200º C por unos 25 minutos para que se haga bien.'),"+
            "(24,5,1,'Es muy importante para elaborar esta receta que el pollo esté perfectamente deshuesado, por eso si no tienes mucha práctica cómpralo ya limpio. Una vez en casa, antes de meterlo en la nevera, límpialo bien con agua del grifo para eliminar la sangre y las posibles plumas que queden pegadas a la carne, luego reserva en la nevera.'),"+
            "(25,5,2,'Al día siguiente, pon el horno en marcha a 200º C para precalentarlo y que esté listo al momento de cocinar tu pollo relleno de Navidad.'),"+
            "(26,5,3,'Trocea los albaricoques secos en trozos pequeños, mientras que el tocino lo cortas en tiras que tengan un dedo de grosor.'),"+
            "(27,5,4,'Coloca la carne picada en un plato hondo y bate el huevo, luego vértelo sobre la carne. Esparce las dos cucharadas de orégano, la cucharada de sal, la de pimienta y comienza a amasar usando las manos para que todos los ingredientes se integren correctamente.'),"+
            "(28,5,5,'A continuación agrega la nuez moscada, la maicena y vuelve a amasar los ingredientes. Agrega el albaricoque y el tocino y de nuevo amasa hasta que estos queden ocultos en la carne.'),"+
            "(29,5,6,'Unta el interior del pollo con la manteca de cerdo y comienza a introducir la mezcla que has hecho hasta que hayas utilizado toda. Con unos palillos sella el agujero para evitar que el relleno se salga durante la cocción de tu pollo de Navidad.'),"+
            "(30,5,7,'Pela las patatas y lávalas con agua fría. Luego las cortas en rodajas que repartirás sobre una bandeja cubierta con papel para horno. Deja un espacio en medio para el pollo.'),"+
            "(31,5,8,'Coloca el pollo en el espacio vacío y baña todos los ingredientes con el vino de Jerez. Puedes usar en su lugar, si lo deseas, vino blanco. Coloca la bandeja justo en medio del horno y deja cocinar durante una hora y media.'),"+
            "(32,5,9,'Para evitar que el pollo se seque, cada 15 minutos báñalo con el jugo que hay en la bandeja. Al pasar 45 minutos puedes darle la vuelta para que se haga bien por ambas partes. En el momento en el que las patatas estén blandas y con un ligero color dorado, sácalas de la bandeja y continúa cocinando el pollo.'),"+
            "(33,5,10,'Cuando tu pollo relleno de Navidad esté listo, déjalo reposar dentro del horno para que termine de hacerse y así esté aún caliente al servirlo.'),"+
            "(34,5,11,'Con esta receta tan sencilla y deliciosa quedarás genial. ¡Lúcete con este plato estrella y deleita a los tuyos en Navidad!'),"+
            "(35,6,1,'Si vamos a preparar este plato, los garbanzos tienen que estar tiernos. Para conseguirlo, lo que hay que hacer es ponerlos a remojo en un cuenco con mucha agua, y un poco de sal, la noche anterior al preparar este plato.'),"+
            "(36,6,2,'Al día siguiente, los colamos, escurrimos y ponemos en una olla grande con bastante agua y un poco de sal. Los ponemos a cocer.'),"+
            "(37,6,3,'Procedemos a pelar la zanahoria y la cebolla. Lavamos ambos ingredientes en agua fría, la cebolla para evitar el efecto lacrimógeno tan fuerte que tiene, y troceamos en rodajas. Echamos con los garbanzos. Removemos y dejamos unos minutos hasta que hierva.'),"+
            "(38,6,4,'En el momento que el agua burbujee echamos el jamón, la panceta y los chorizos. Ponemos la tapa y dejamos que todo se cueza por alrededor de 20 minutos. Controladlo de vez en cuando, y si veis que el caldo se queda sin agua, pues echad más agua pero que esté bien caliente.'),"+
            "(39,6,5,'Cuando los garbanzos estén tiernos, retiramos del fuego la olla. Sacamos el chorizo, el jamón, la carne y la panceta y colocamos en un plato. Los garbanzos por otro lado los sacamos y escurrimos para que suelten todo el caldo.'),"+
            "(40,6,6,'El caldo que hemos preparado lo colamos y ponemos en una cazuela grande de barro. Echamos los fideos y cocemos a fuego lento por unos minutos.'),"+
            "(41,6,7,'Mientras haremos un sofrito: pelamos los dientes de ajo y picamos. Los pimientos los cortamos en trozos y todo esto lo freímos en una sartén, hasta que cojan color. Una vez listo, se añade el chorizo, el jamón y la panceta en trozos.'),"+
            "(42,6,8,'Echamos los garbanzos a la cazuela de barro y dejamos un par de minutos para que los fideos estén listos. Servimos los platos bien calientes y en una bandeja aparte servimos el sofrito para que coja cada uno lo que más le apetezca.'),"+
            "(43,7,1,'Pon la miga de pan en un bol grande. Vierte encima agua fría y deja remojar.'),"+
            "(44,7,2,'Casca las almendras y escáldalas durante un par de minutos en una cazuela con agua hirviendo. Retírales la piel con una puntilla y añádelas al vaso batidor.'),"+
            "(45,7,3,'Agrega los ajos, vinagre y sal a tu gusto y tritura hasta conseguir una crema homogénea. Incorpora el aceite poco a poco y continúa triturando. Enfría en el frigorífico.'),"+
            "(46,7,4,'Para la guarnición, haz unas bolitas de melón, pela unas uvas y trocea finamente el jamón. Sirve en cada copa un poco de guarnición y vierte encima el ajo blanco.'),"+
            "(47,8,1,'Primero de todo troceamos la cebolla, unos 90 gr. Procedemos a calentar aceite en una sartén grande y la echamos ahí, pochándola sin llegar a dorarla con un poco de sal.'),"+
            "(48,8,2,'Mientras se pocha la cebolla, podemos ir quitándole la piel al calabacín para luego cortarlo en pequeños pedazos.'),"+
            "(49,8,3,'Cuando la cebolla empiece a dorarse, echamos el calabacín y lo dejamos friendo a fuego medio durante unos 10 – 11 minutos, removiendo de vez en cuando para que no se pegue.'),"+
            "(50,8,4,'Cogemos una cacerola en la que empezaremos a calentar ¼ de caldo de pollo durante 10 minutos. Cuando empiece a hervir un poco, añadimos 30 gr. de mantequilla y removemos bien hasta que se derrita del todo. No os olvidéis de que en la sartén tenéis el calabacín y hay que removerlo de vez en cuando.'),"+
            "(51,8,5,'Con una cuchara, cogemos harina y la añadimos a la sartén en la que está el calabacín, removiéndolo todo bien hasta que se vaya espesando un poquito.'),"+
            "(52,8,6,'Seguidamente, vertemos sobre la sartén el caldo de pollo con la mantequilla ya derretida y fundida, y lo dejamos a fuego medio hasta que el caldo hierva. En ese momento apartar del fuego.'),"+
            "(53,8,7,'Ahora tenemos que usar la batidora, así que lo disponemos todo vertiéndolo en el vaso de esta. Lo batiremos todo hasta que se forme una crema suave. En el caso de notar que se va quedando algo espesa, podemos echar más caldo de pollo.'),"+
            "(54,8,8,'Es el turno de echar la nata líquido, con 1/5 de litro bastará, pero esta vez lo removeremos todo con una cucharada de palo.'),"+
            "(55,8,9,'Ya está lista para servir. ¡A disfrutar!'),"+
            "(56,9,1,'Primero de todo pela y corta la cebolla, lávala un poco bajo el agua del grifo y así su efecto lacrimógeno no será tan fuerte, y la pochas en una sartén con un poco de aceite de oliva.'),"+
            "(57,9,2,'Mientras la cebolla se pocha cortas en tiras los pimientos, los tomates en trocitos pequeños. Cuando la cebolla esté trasnparente lo echas todo y dejas que se doren.'),"+
            "(58,9,3,'Los huevos los necesitas duros, de modo que ponlos en una cacerola con bastante agua para que se cuezan por 10 minutos. Eso si, deja un huevo apartado.'),"+
            "(59,9,4,'El huevo que reservas vas a batirlo con un poco de sal, y cuando el sofrito esté listo, lo echas dentro y remueve bien.'),"+
            "(60,9,5,'Ahora es el momento de desmenuzar bien el bonito, tras haberlo limpiado bien.'),"+
            "(61,9,6,'Extiende una de las láminas de la masa y pon repartido el bonito por toda ella. También le vas a agregar los huevos duros bien troceados (los puedes triturar si lo deseas).'),"+
            "(62,9,7,'Luego agrega el sofrito. A continuación pon la otra lámina por encima y sella los bordes tan bien como puedas.'),"+
            "(63,9,8,'Pon el horno en marcha a 180º C y mete la empanada de bonito que has hecho. Que se haga durante 30 minutos.'),"+
            "(64,9,9,'Al sacarla déjala reposar un poco antes de comerlo. Si quieres que esté más dorada y crujiente, puedes batir un huevo y pintar la parte superior de la lámina antes de meterla en el horno.'),"+
            "(65,10,1,'Pela la cebolla, lávala bajo el agua del grifo y pícala en cubos pequeños, después pela y pica el diente de ajo y reserva. Calienta a fuego medio una sartén con la cucharada de aceite de oliva, una vez caliente añade los ingredientes anteriores.'),"+
            "(66,10,2,'Mientras se sofríen, trocea el bacon en tiras y reserva. Remueve con frecuencia la cebolla y el ajo y, de ser necesario, baja el fuego para evitar que se quemen.'),"+
            "(67,10,3,'A continuación limpia los champiñones para quitarles la tierra y córtalos en tiras que no sean demasiado gruesas. Cuando la cebolla tenga un tono transparente, agrega a la sartén tanto el bacon como los champiñones y remueve bien mientras se cocina todo a fuego medio.'),"+
            "(68,10,4,'Al coger un poco de color, reduce la potencia del fuego y cocina lentamente, removiendo de vez en cuando. Es importante que el relleno de tu quiche de bacon y champiñones quede perfectamente hecho, sin quemarse. Luego, enciende el horno a 180 ºC para precalentarlo.'),"+
            "(69,10,5,'Mientras se cocina lo que tienes en la sartén, enharina o unta con mantequilla un molde y coloca la masa quebrada en su interior, encajándola bien. Asegúrate de que las masa cubra también los laterales del molde para que la mezcla de la quiche se mantenga en su lugar.'),"+
            "(70,10,6,'Métela en el horno a 180 ºC durante 10 minutos para que se cocine ligeramente. Tras ese tiempo sácala y mantén el horno a esa temperatura pues lo usarás nuevamente en breve.'),"+
            "(71,10,7,'Bate los huevos junto con la sal y la pimienta hasta haberlos integrado en una mezcla homogénea. Después vierte la nata sin dejar de batir un solo momento con una batidora de brazo o unas varillas eléctricas. Comprueba el sabor de la mezcla y de ser necesario salpimienta de nuevo.'),"+
            "(72,10,8,'Mezcla los vegetales y el bacon que has cocinado en la sartén con el huevo y la nata, este será el relleno de tu quiche de bacon y champiñones.'),"+
            "(73,10,9,'Con tu mezcla ya lista, échala sobre la masa que has horneado antes repartiéndola bien con ayuda de una espátula. Tienes que asegurarte de que no quede ni un solo espacio de la masa sin cubrir.'),"+
            "(74,10,10,'Añade el queso rallado emmental por encima y prepárate a meter al horno esta deliciosa quiche de bacon y champiñones durante 15 a 20 minutos o hasta que la mezcla esté cuajada.'),"+
            "(75,10,11,'Una vez lista, retira del horno y deja reposar 30 minutos antes de cortarla. ¡Verás que deliciosa está!'),"+
            "(76,11,1,'En un cazo ponemos él agua la mantequilla y la pizca de sal y llevamos a ebullición. Una vez cueza echamos la harina previamente tamizada y movemos en él fuego hasta que la masa se separe de las paredes y él fondo. Retiramos y ponemos sobre un papel de horno, dejamos que se enfríe.'),"+
            "(77,11,2,'Una vez fría añadimos los huevos primero uno, hasta que no este bien mezclado con la masa no añadimos él otro.'),"+
            "(78,11,3,'Cuando estén los huevos bien mezclados con la masa metemos en una manga pastelera con boquilla redonda. En la bandeja del horno ponemos papel de hornear y vamos haciendo bolitas con la masa dejando espacio entre ellas.'),"+
            "(79,11,4,'Precalentamos él horno a 180gr. Horneamos unos 15 o 20 minutos.'),"+
            "(80,11,5,'Sacamos del horno y dejamos enfriar. Mientras montamos la nata con él azucar. Una vez fríos los profiteroles les hacemos un corte y rellenamos con la nata. Cubrimos con un poco de chocolate y sirope de fresa.'),"+
            "(81,12,1,'Ponga la nata en un bol y mézclela con un poco de azúcar para montarla. Entonces agregue el queso mascarpone, el queso philadelphia y algo más de azúcar. Con una batidora mezcle todos los ingredientes hasta que obtenga una masa cremosa uniforme.'),"+
            "(82,12,2,'Haga el café y viértalo en un bol, aun estando caliente, junto con el brandy. Vaya remojando todas las galletas para empaparlas con el café, pero sin dejar que se queden blandas del todo. Coloque las galletas en el molde para cubrir el fondo por completo.'),"+
            "(83,12,3,'Cuando haya cubierto todo el fondo, agregue una capa de la crema que ha hecho y cubra esta con una capa de galletas mojadas en café. Vuelva a cubrir con otra capa de la crema y por último una nueva capa de galletas. Vierta el resto de tiramisú que le queda y meta en la nevera varias horas para que coja consistencia.'),"+
            "(84,12,4,'Antes de servir, ponga chocolate amargo por la superficie y también algo de nata, para que haga bonito.')";
}
