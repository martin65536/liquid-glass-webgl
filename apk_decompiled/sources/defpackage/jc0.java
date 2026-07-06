package defpackage;

import android.app.Application;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.drm.DrmManagerClient;
import android.graphics.Shader;
import android.media.MediaDrm;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.Display;
import android.view.RoundedCorner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;

/* loaded from: classes.dex */
public abstract class jc0 {
    public static final pb b;
    public static final ey0 m;
    public static final gg a = new gg(-1833280673, false, new w4(2, 0));
    public static final pb c = new pb(4);
    public static final float[] d = {1.0f, 10.0f, 100.0f, 1000.0f, 10000.0f, 100000.0f, 1000000.0f, 1.0E7f, 1.0E8f, 1.0E9f, 1.0E10f};
    public static final long[] e = {-6499023860262858360L, -3512093806901185046L, -9112587656954322510L, -6779048552765515233L, -3862124672529506138L, -215969822234494768L, -7052510166537641086L, -4203951689744663454L, -643253593753441413L, -7319562523736982739L, -4537767136243840520L, -1060522901877412746L, -7580355841314464822L, -4863758783215693124L, -1468012460592228501L, -7835036815511224669L, -5182110000961642932L, -1865951482774665761L, -8083748704375247957L, -5492999862041672042L, -2254563809124702148L, -8326631408344020699L, -5796603242002637969L, -2634068034075909558L, -8563821548938525330L, -6093090917745768758L, -3004677628754823043L, -8795452545612846258L, -6382629663588669919L, -3366601061058449494L, -9021654690802612790L, -6665382345075878084L, -3720041912917459700L, -38366372719436721L, -6941508010590729807L, -4065198994811024355L, -469812725086392539L, -7211161980820077193L, -4402266457597708587L, -891147053569747830L, -7474495936122174250L, -4731433901725329908L, -1302606358729274481L, -7731658001846878407L, -5052886483881210105L, -1704422086424124727L, -7982792831656159810L, -5366805021142811859L, -2096820258001126919L, -8228041688891786181L, -5673366092687344822L, -2480021597431793123L, -8467542526035952558L, -5972742139117552794L, -2854241655469553088L, -8701430062309552536L, -6265101559459552766L, -3219690930897053053L, -8929835859451740015L, -6550608805887287114L, -3576574988931720989L, -9152888395723407474L, -6829424476226871438L, -3925094576856201394L, -294682202642863838L, -7101705404292871755L, -4265445736938701790L, -720121152745989333L, -7367604748107325189L, -4597819916706768583L, -1135588877456072824L, -7627272076051127371L, -4922404076636521310L, -1541319077368263733L, -7880853450996246689L, -5239380795317920458L, -1937539975720012668L, -8128491512466089774L, -5548928372155224313L, -2324474446766642487L, -8370325556870233411L, -5851220927660403859L, -2702340141148116920L, -8606491615858654931L, -6146428501395930760L, -3071349608317525546L, -8837122532839535322L, -6434717147622031249L, -3431710416100151157L, -9062348037703676329L, -6716249028702207507L, -3783625267450371480L, -117845565885576446L, -6991182506319567135L, -4127292114472071014L, -547429124662700864L, -7259672230555269896L, -4462904269766699466L, -966944318780986428L, -7521869226879198374L, -4790650515171610063L, -1376627125537124675L, -7777920981101784778L, -5110715207949843068L, -1776707991509915931L, -8027971522334779313L, -5423278384491086237L, -2167411962186469893L, -8272161504007625539L, -5728515861582144020L, -2548958808550292121L, -8510628282985014432L, -6026599335303880135L, -2921563150702462265L, -8743505996830120772L, -6317696477610263061L, -3285434578585440922L, -8970925639256982432L, -6601971030643840136L, -3640777769877412266L, -9193015133814464522L, -6879582898840692749L, -3987792605123478032L, -373054737976959636L, -7150688238876681629L, -4326674280168464132L, -796656831783192261L, -7415439547505577019L, -4657613415954583370L, -1210330751515841308L, -7673985747338482674L, -4980796165745715438L, -1614309188754756393L, -7926472270612804602L, -5296404319838617848L, -2008819381370884406L, -8173041140997884610L, -5604615407819967859L, -2394083241347571919L, -8413831053483314306L, -5905602798426754978L, -2770317479606055818L, -8648977452394866743L, -6199535797066195524L, -3137733727905356501L, -8878612607581929669L, -6486579741050024183L, -3496538657885142324L, -9102865688819295809L, -6766896092596731857L, -3846934097318526917L, -196981603220770742L, -7040642529654063570L, -4189117143640191558L, -624710411122851544L, -7307973034592864071L, -4523280274813692185L, -1042414325089727327L, -7569037980822161435L, -4849611457600313890L, -1450328303573004458L, -7823984217374209643L, -5168294253290374149L, -1848681798185579782L, -8072955151507069220L, -5479507920956448621L, -2237698882768172872L, -8316090829371189901L, -5783427518286599473L, -2617598379430861437L, -8553528014785370254L, -6080224000054324913L, -2988593981640518238L, -8785400266166405755L, -6370064314280619289L, -3350894374423386208L, -9011838011655698236L, -6653111496142234891L, -3704703351750405709L, -19193171260619233L, -6929524759678968877L, -4050219931171323192L, -451088895536766085L, -7199459587351560659L, -4387638465762062920L, -872862063775190746L, -7463067817500576073L, -4717148753448332187L, -1284749923383027329L, -7720497729755473937L, -5038936143766954517L, -1686984161281305242L, -7971894128441897632L, -5353181642124984136L, -2079791034228842266L, -8217398424034108273L, -5660062011615247437L, -2463391496091671392L, -8457148712698376476L, -5959749872445582691L, -2838001322129590460L, -8691279853972075893L, -6252413799037706963L, -3203831230369745799L, -8919923546622172981L, -6538218414850328322L, -3561087000135522498L, -9143208402725783417L, -6817324484979841368L, -3909969587797413806L, -275775966319379353L, -7089889006590693952L, -4250675239810979535L, -701658031336336515L, -7356065297226292178L, -4583395603105477319L, -1117558485454458744L, -7616003081050118571L, -4908317832885260310L, -1523711272679187483L, -7869848573065574033L, -5225624697904579637L, -1920344853953336643L, -8117744561361917258L, -5535494683275008668L, -2307682335666372931L, -8359830487432564938L, -5838102090863318269L, -2685941595151759932L, -8596242524610931813L, -6133617137336276863L, -3055335403242958174L, -8827113654667930715L, -6422206049907525490L, -3416071543957018958L, -9052573742614218705L, -6704031159840385477L, -3768352931373093942L, -98755145788979524L, -6979250993759194058L, -4112377723771604669L, -528786136287117932L, -7248020362820530564L, -4448339435098275301L, -948738275445456222L, -7510490449794491995L, -4776427043815727089L, -1358847786342270957L, -7766808894105001205L, -5096825099203863602L, -1759345355577441598L, -8017119874876982855L, -5409713825168840664L, -2150456263033662926L, -8261564192037121185L, -5715269221619013577L, -2532400508596379068L, -8500279345513818773L, -6013663163464885563L, -2905392935903719049L, -8733399612580906262L, -6305063497298744923L, -3269643353196043250L, -8961056123388608887L, -6589634135808373205L, -3625356651333078602L, -9183376934724255983L, -6867535149977932074L, -3972732919045027189L, -354230130378896082L, -7138922859127891907L, -4311967555482476980L, -778273425925708321L, -7403949918844649557L, -4643251380128424042L, -1192378206733142148L, -7662765406849295699L, -4966770740134231719L, -1596777406740401745L, -7915514906853832947L, -5282707615139903279L, -1991698500497491195L, -8162340590452013853L, -5591239719637629412L, -2377363631119648861L, -8403381297090862394L, -5892540602936190089L, -2753989735242849707L, -8638772612167862923L, -6186779746782440750L, -3121788665050663033L, -8868646943297746252L, -6474122660694794911L, -3480967307441105734L, -9093133594791772940L, -6754730975062328271L, -3831727700400522434L, -177973607073265139L, -7028762532061872568L, -4174267146649952806L, -606147914885053103L, -7296371474444240046L, -4508778324627912153L, -1024286887357502287L, -7557708332239520786L, -4835449396872013078L, -1432625727662628443L, -7812920107430224633L, -5154464115860392887L, -1831394126398103205L, -8062150356639896359L, -5466001927372482545L, -2220816390788215277L, -8305539271883716405L, -5770238071427257602L, -2601111570856684098L, -8543223759426509417L, -6067343680855748868L, -2972493582642298180L, -8775337516792518219L, -6357485877563259869L, -3335171328526686933L, -9002011107970261189L, -6640827866535438582L, -3689348814741910324L, Long.MIN_VALUE, -6917529027641081856L, -4035225266123964416L, -432345564227567616L, -7187745005283311616L, -4372995238176751616L, -854558029293551616L, -7451627795949551616L, -4702848726509551616L, -1266874889709551616L, -7709325833709551616L, -5024971273709551616L, -1669528073709551616L, -7960984073709551616L, -5339544073709551616L, -2062744073709551616L, -8206744073709551616L, -5646744073709551616L, -2446744073709551616L, -8446744073709551616L, -5946744073709551616L, -2821744073709551616L, -8681119073709551616L, -6239712823709551616L, -3187955011209551616L, -8910000909647051616L, -6525815118631426616L, -3545582879861895366L, -9133518327554766460L, -6805211891016070171L, -3894828845342699810L, -256850038250986858L, -7078060301547948643L, -4235889358507547899L, -683175679707046970L, -7344513827457986212L, -4568956265895094861L, -1099509313941480672L, -7604722348854507276L, -4894216917640746191L, -1506085128623544835L, -7858832233030797378L, -5211854272861108819L, -1903131822648998119L, -8106986416796705681L, -5522047002568494197L, -2290872734783229842L, -8349324486880600507L, -5824969590173362730L, -2669525969289315508L, -8585982758446904049L, -6120792429631242157L, -3039304518611664792L, -8817094351773372351L, -6409681921289327535L, -3400416383184271515L, -9042789267131251553L, -6691800565486676537L, -3753064688430957767L, -79644842111309304L, -6967307053960650171L, -4097447799023424810L, -510123730351893109L, -7236356359111015049L, -4433759430461380907L, -930513269649338230L, -7499099821171918250L, -4762188758037509908L, -1341049929119499481L, -7755685233340769032L, -5082920523248573386L, -1741964635633328828L, -8006256924911912374L, -5396135137712502563L, -2133482903713240300L, -8250955842461857044L, -5702008784649933400L, -2515824962385028846L, -8489919629131724885L, -6000713517987268202L, -2889205879056697349L, -8723282702051517699L, -6292417359137009220L, -3253835680493873621L, -8951176327949752869L, -6577284391509803182L, -3609919470959866074L, -9173728696990998152L, -6855474852811359786L, -3957657547586811828L, -335385916056126881L, -7127145225176161157L, -4297245513042813542L, -759870872876129024L, -7392448323188662496L, -4628874385558440216L, -1174406963520662366L, -7651533379841495835L, -4952730706374481889L, -1579227364540714458L, -7904546130479028392L, -5268996644671397586L, -1974559787411859078L, -8151628894773493780L, -5577850100039479321L, -2360626606621961247L, -8392920656779807636L, -5879464802547371641L, -2737644984756826647L, -8628557143114098510L, -6174010410465235234L, -3105826994654156138L, -8858670899299929442L, -6461652605697523899L, -3465379738694516970L, -9083391364325154962L, -6742553186979055799L, -3816505465296431844L, -158945813193151901L, -7016870160886801794L, -4159401682681114339L, -587566084924005019L, -7284757830718584993L, -4494261269970843337L, -1006140569036166268L, -7546366883288685774L, -4821272585683469313L, -1414904713676948737L, -7801844473689174817L, -5140619573684080617L, -1814088448677712867L, -8051334308064652398L, -5452481866653427593L, -2203916314889396588L, -8294976724446954723L, -5757034887131305500L, -2584607590486743971L, -8532908771695296838L, -6054449946191733143L, -2956376414312278525L, -8765264286586255934L, -6344894339805432014L, -3319431906329402113L, -8992173969096958177L, -6628531442943809817L, -3673978285252374367L, -9213765455923815836L, -6905520801477381891L, -4020214983419339459L, -413582710846786420L, -7176018221920323369L, -4358336758973016307L, -836234930288882479L, -7440175859071633406L, -4688533805412153853L, -1248981238337804412L, -7698142301602209614L, -5010991858575374113L, -1652053804791829737L, -7950062655635975442L, -5325892301117581398L, -2045679357969588844L, -8196078626372074883L, -5633412264537705700L, -2430079312244744221L, -8436328597794046994L, -5933724728815170839L, -2805469892591575644L, -8670947710510816634L, -6226998619711132888L, -3172062256211528206L, -8900067937773286985L, -6513398903789220827L, -3530062611309138130L, -9123818159709293187L, -6793086681209228580L, -3879672333084147821L, -237904397927796872L, -7066219276345954901L, -4221088077005055722L, -664674077828931749L, -7332950326284164199L, -4554501889427817345L, -1081441343357383777L, -7593429867239446717L, -4880101315621920492L, -1488440626100012711L, -7847804418953589800L, -5198069505264599346L, -1885900863153361279L, -8096217067111932656L, -5508585315462527915L, -2274045625900771990L, -8338807543829064350L, -5811823411358942533L, -2653093245771290262L, -8575712306248138270L, -6107954364382784934L, -3023256937051093263L, -8807064613298015146L, -6397144748195131028L, -3384744916816525881L, -9032994600651410532L, -6679557232386875260L, -3737760522056206171L, -60514634142869810L, -6955350673980375487L, -4082502324048081455L, -491441886632713915L, -7224680206786528053L, -4419164240055772162L, -912269281642327298L, -7487697328667536418L, -4747935642407032618L, -1323233534581402868L, -7744549986754458649L, -5069001465015685407L, -1724565812842218855L, -7995382660667468640L, -5382542307406947896L, -2116491865831296966L, -8240336443785642460L, -5688734536304665171L, -2499232151953443560L, -8479549122611984081L, -5987750384837592197L, -2873001962619602342L, -8713155254278333320L, -6279758049420528746L, -3238011543348273028L, -8941286242233752499L, -6564921784364802720L, -3594466212028615495L, -9164070410158966541L, -6843401994271320272L, -3942566474411762436L, -316522074587315140L, -7115355324258153819L, -4282508136895304370L, -741449152691742558L, -7380934748073420955L, -4614482416664388289L, -1156417002403097458L, -7640289654143017767L, -4938676049251384305L, -1561659043136842477L, -7893565929601608404L, -5255271393574622601L, -1957403223540890347L, -8140906042354138323L, -5564446534515285000L, -2343872149716718346L, -8382449121214030822L, -5866375383090150624L, -2721283210435300376L, -8618331034163144591L, -6161227774276542835L, -3089848699418290639L, -8848684464777513506L, -6449169562544503978L, -3449775934753242068L, -9073638986861858149L, -6730362715149934782L, -3801267375510030573L, -139898200960150313L, -7004965403241175802L, -4144520735624081848L, -568964901102714406L, -7273132090830278360L, -4479729095110460046L, -987975350460687153L, -7535013621679011327L, -4807081008671376254L, -1397165242411832414L, -7790757304148477115L, -5126760611758208489L, -1796764746270372707L, -8040506994060064798L, -5438947724147693094L, -2186998636757228463L, -8284403175614349646L, -5743817951090549153L, -2568086420435798537L, -8522583040413455942L, -6041542782089432023L, -2940242459184402125L, -8755180564631333184L, -6332289687361778576L, -3303676090774835316L, -8982326584375353929L, -6616222212041804507L, -3658591746624867729L, -9204148869281624187L, -6893500068174642330L, -4005189066790915008L, -394800315061255856L, -7164279224554366766L, -4343663012265570553L, -817892746904575288L, -7428711994456441411L, -4674203974643163860L, -1231068949876566920L, -7686947121313936181L, -4996997883215032323L, -1634561335591402499L, -7939129862385708418L, -5312226309554747619L, -2028596868516046619L, -8185402070463610993L};
    public static final pb f = new pb(8);
    public static final Object g = new Object();
    public static final StackTraceElement[] h = new StackTraceElement[0];
    public static final rt i = new rt(27);
    public static final rt j = new rt(28);
    public static final rt k = new rt(29);
    public static final wq l = new wq("NO_VALUE", 1);
    public static final Object n = new Object();
    public static final m41 o = new m41(18);
    public static final m41 p = new m41(19);
    public static final m41 q = new m41(20);
    public static final m41 r = new m41(21);

    static {
        int i2 = 3;
        b = new pb(i2);
        m = new ey0(i2);
    }

    public static final Object A(ll0 ll0Var, do0 do0Var) {
        do0Var.getClass();
        Object obj = ll0Var.get(do0Var);
        if (obj == null) {
            obj = do0Var.b();
        }
        return ((i41) obj).a(ll0Var);
    }

    public static List B(Resources resources, int i2) {
        if (i2 == 0) {
            return Collections.EMPTY_LIST;
        }
        TypedArray obtainTypedArray = resources.obtainTypedArray(i2);
        try {
            if (obtainTypedArray.length() == 0) {
                return Collections.EMPTY_LIST;
            }
            ArrayList arrayList = new ArrayList();
            if (obtainTypedArray.getType(0) == 1) {
                for (int i3 = 0; i3 < obtainTypedArray.length(); i3++) {
                    int resourceId = obtainTypedArray.getResourceId(i3, 0);
                    if (resourceId != 0) {
                        String[] stringArray = resources.getStringArray(resourceId);
                        ArrayList arrayList2 = new ArrayList();
                        for (String str : stringArray) {
                            arrayList2.add(Base64.decode(str, 0));
                        }
                        arrayList.add(arrayList2);
                    }
                }
            } else {
                String[] stringArray2 = resources.getStringArray(i2);
                ArrayList arrayList3 = new ArrayList();
                for (String str2 : stringArray2) {
                    arrayList3.add(Base64.decode(str2, 0));
                }
                arrayList.add(arrayList3);
            }
            return arrayList;
        } finally {
            obtainTypedArray.recycle();
        }
    }

    public static final gg C(int i2, sv svVar, bw bwVar) {
        Object L = bwVar.L();
        boolean z = true;
        if (L == ph.a) {
            L = new gg(i2, true, svVar);
            bwVar.f0(L);
        }
        gg ggVar = (gg) L;
        if (!o20.e(ggVar.g, svVar)) {
            if (ggVar.g != null) {
                z = false;
            }
            ggVar.g = svVar;
            if (!z && ggVar.f) {
                mo0 mo0Var = ggVar.h;
                if (mo0Var != null) {
                    yh yhVar = mo0Var.a;
                    if (yhVar != null) {
                        yhVar.s(mo0Var, null);
                    }
                    ggVar.h = null;
                }
                ArrayList arrayList = ggVar.i;
                if (arrayList != null) {
                    int size = arrayList.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        mo0 mo0Var2 = (mo0) arrayList.get(i3);
                        yh yhVar2 = mo0Var2.a;
                        if (yhVar2 != null) {
                            yhVar2.s(mo0Var2, null);
                        }
                    }
                    arrayList.clear();
                }
            }
        }
        return ggVar;
    }

    public static final kl D(gv gvVar, bw bwVar) {
        af0 D = n30.D(gvVar, bwVar);
        Object L = bwVar.L();
        if (L == ph.a) {
            kl klVar = new kl(new ep(D, 0));
            bwVar.f0(klVar);
            L = klVar;
        }
        return (kl) L;
    }

    public static final c40 E(bw bwVar) {
        hx a2 = mx.a(bwVar);
        boolean f2 = bwVar.f(a2) | bwVar.f(f);
        Object L = bwVar.L();
        if (f2 || L == ph.a) {
            L = new c40(a2);
            bwVar.f0(L);
        }
        return (c40) L;
    }

    public static final boolean F(mo0 mo0Var, mo0 mo0Var2) {
        if (mo0Var != null && mo0Var.a() && mo0Var != mo0Var2 && !o20.e(mo0Var.c, mo0Var2.c)) {
            return false;
        }
        return true;
    }

    public static int G(float f2) {
        if (!Float.isNaN(f2)) {
            return Math.round(f2);
        }
        v7.m("Cannot round NaN value.");
        return 0;
    }

    public static void H() {
        throw new ArithmeticException("Index overflow has happened.");
    }

    public static final Shader.TileMode I(int i2) {
        Shader.TileMode tileMode;
        if (i2 == 0) {
            return Shader.TileMode.CLAMP;
        }
        if (i2 == 1) {
            return Shader.TileMode.REPEAT;
        }
        if (i2 == 2) {
            return Shader.TileMode.MIRROR;
        }
        if (i2 == 3) {
            if (Build.VERSION.SDK_INT >= 31) {
                tileMode = Shader.TileMode.DECAL;
                return tileMode;
            }
            return Shader.TileMode.CLAMP;
        }
        return Shader.TileMode.CLAMP;
    }

    public static final ll0 J(eo0[] eo0VarArr, ll0 ll0Var, ll0 ll0Var2) {
        kl0 kl0Var = new kl0(ll0.h);
        for (eo0 eo0Var : eo0VarArr) {
            do0 do0Var = eo0Var.a;
            if (eo0Var.f || !ll0Var.containsKey(do0Var)) {
                kl0Var.put(do0Var, do0Var.c(eo0Var, (i41) ll0Var2.get(do0Var)));
            }
        }
        return kl0Var.b();
    }

    public static final cd0 K(cd0 cd0Var, gv gvVar) {
        return cd0Var.b(new b01(gvVar));
    }

    public static final Object L(yj yjVar, Object obj, Object obj2, kv kvVar, ij ijVar) {
        Object d2;
        Object Q = k81.Q(yjVar, obj2);
        try {
            by0 by0Var = new by0(ijVar, yjVar);
            if (!d3.A(kvVar)) {
                d2 = t20.U(kvVar, obj, by0Var);
            } else {
                f31.n(2, kvVar);
                d2 = kvVar.d(obj, by0Var);
            }
            k81.G(yjVar, Q);
            if (d2 == ik.e) {
                ijVar.getClass();
            }
            return d2;
        } catch (Throwable th) {
            k81.G(yjVar, Q);
            throw th;
        }
    }

    public static final int M(float f2, float[] fArr, int i2) {
        float f3 = 0.0f;
        if (f2 >= 0.0f) {
            f3 = f2;
        }
        if (f3 > 1.0f) {
            f3 = 1.0f;
        }
        if (Math.abs(f3 - f2) > 1.05E-6f) {
            f3 = Float.NaN;
        }
        fArr[i2] = f3;
        return !Float.isNaN(f3) ? 1 : 0;
    }

    public static final void a(bw bwVar, int i2) {
        boolean z;
        float f2;
        long j2;
        int i3;
        y6 y6Var;
        Object m2Var;
        cd0 cd0Var;
        bwVar.W(-1208095824);
        if (i2 != 0) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i2 & 1, z)) {
            boolean D = n20.D(bwVar);
            boolean z2 = !D;
            hx a2 = mx.a(bwVar);
            boolean g2 = bwVar.g(z2);
            Object L = bwVar.L();
            Object obj = ph.a;
            if (g2 || L == obj) {
                if (!D) {
                    f2 = 1.0f;
                } else {
                    f2 = 0.0f;
                }
                L = dl.a(f2, 0.01f);
                bwVar.f0(L);
            }
            y6 y6Var2 = (y6) L;
            boolean g3 = bwVar.g(z2);
            Object L2 = bwVar.L();
            ij ijVar = null;
            if (g3 || L2 == obj) {
                if (!D) {
                    j2 = se.b;
                } else {
                    j2 = se.c;
                }
                L2 = new y6(new se(j2), new c4(23, w3.j, new q2(6, se.f(j2))), null, 12);
                bwVar.f0(L2);
            }
            y6 y6Var3 = (y6) L2;
            boolean h2 = bwVar.h(a2) | bwVar.h(y6Var3) | bwVar.h(y6Var2);
            Object L3 = bwVar.L();
            if (h2 || L3 == obj) {
                i3 = 12;
                y6Var = y6Var3;
                m2Var = new m2(a2, y6Var2, y6Var, ijVar, 0);
                cd0Var = null;
                bwVar.f0(m2Var);
            } else {
                i3 = 12;
                y6Var = y6Var3;
                m2Var = L3;
                cd0Var = null;
            }
            x1 x1Var = hx.y;
            dl.i((kv) m2Var, bwVar, a2);
            Object L4 = bwVar.L();
            if (L4 == obj) {
                L4 = dl.r(bwVar);
                bwVar.f0(L4);
            }
            hk hkVar = (hk) L4;
            Object L5 = bwVar.L();
            if (L5 == obj) {
                L5 = new y6(new ch0(0L), dl.u, cd0Var, i3);
                bwVar.f0(L5);
            }
            y6 y6Var4 = (y6) L5;
            Object L6 = bwVar.L();
            if (L6 == obj) {
                L6 = dl.a(1.0f, 0.01f);
                bwVar.f0(L6);
            }
            y6 y6Var5 = (y6) L6;
            Object L7 = bwVar.L();
            if (L7 == obj) {
                L7 = dl.a(0.0f, 0.01f);
                bwVar.f0(L7);
            }
            f31.b(cd0Var, C(-738735547, new w2(y6Var2, y6Var4, y6Var5, (y6) L7, a2, hkVar, y6Var), bwVar), bwVar, 48, 1);
        } else {
            bwVar.R();
        }
        mo0 r2 = bwVar.r();
        if (r2 != null) {
            r2.d = new yu0(i2, 3, false);
        }
    }

    public static final rm b(Context context) {
        float f2 = context.getResources().getConfiguration().fontScale;
        float f3 = context.getResources().getDisplayMetrics().density;
        iu a2 = ju.a(f2);
        if (a2 == null) {
            a2 = new z80(f2);
        }
        return new rm(f3, f2, a2);
    }

    public static final long c(float f2, boolean z, boolean z2) {
        long j2;
        long floatToRawIntBits = Float.floatToRawIntBits(f2);
        long j3 = 0;
        if (z) {
            j2 = 1;
        } else {
            j2 = 0;
        }
        if (z2) {
            j3 = 2;
        }
        return ((j2 | j3) & 4294967295L) | (floatToRawIntBits << 32);
    }

    public static final void d(cd0 cd0Var, kv kvVar, bw bwVar, int i2) {
        int i3;
        boolean z;
        int i4;
        int i5;
        bwVar.W(-1298353104);
        if ((i2 & 6) == 0) {
            if (bwVar.f(cd0Var)) {
                i5 = 4;
            } else {
                i5 = 2;
            }
            i3 = i5 | i2;
        } else {
            i3 = i2;
        }
        if ((i2 & 48) == 0) {
            if (bwVar.h(kvVar)) {
                i4 = 32;
            } else {
                i4 = 16;
            }
            i3 |= i4;
        }
        if ((i3 & 19) != 18) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i3 & 1, z)) {
            Object L = bwVar.L();
            if (L == ph.a) {
                L = new hz0(x1.T);
                bwVar.f0(L);
            }
            e((hz0) L, cd0Var, kvVar, bwVar, (i3 << 3) & 1008);
        } else {
            bwVar.R();
        }
        mo0 r2 = bwVar.r();
        if (r2 != null) {
            r2.d = new o9(cd0Var, kvVar, i2);
        }
    }

    public static final void e(hz0 hz0Var, cd0 cd0Var, kv kvVar, bw bwVar, int i2) {
        int i3;
        boolean z;
        gw gwVar;
        int i4;
        int i5;
        int i6;
        bwVar.W(-511989831);
        if ((i2 & 6) == 0) {
            if (bwVar.h(hz0Var)) {
                i6 = 4;
            } else {
                i6 = 2;
            }
            i3 = i6 | i2;
        } else {
            i3 = i2;
        }
        if ((i2 & 48) == 0) {
            if (bwVar.f(cd0Var)) {
                i5 = 32;
            } else {
                i5 = 16;
            }
            i3 |= i5;
        }
        if ((i2 & 384) == 0) {
            if (bwVar.h(kvVar)) {
                i4 = 256;
            } else {
                i4 = 128;
            }
            i3 |= i4;
        }
        if ((i3 & 147) != 146) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i3 & 1, z)) {
            long j2 = bwVar.T;
            int i7 = (int) ((j2 >>> 32) ^ j2);
            bwVar.T(206, rh.e);
            if (bwVar.S) {
                uw0.z(bwVar.I);
            }
            Object D = bwVar.D();
            if (D instanceof gw) {
                gwVar = (gw) D;
            } else {
                gwVar = null;
            }
            if (gwVar == null) {
                gw gwVar2 = new gw(new yv(new zv(bwVar, bwVar.T, bwVar.q, bwVar.C, bwVar.h.x)), -1);
                bwVar.g0(gwVar2);
                gwVar = gwVar2;
            }
            np0 np0Var = gwVar.a;
            np0Var.getClass();
            zv zvVar = ((yv) np0Var).e;
            zvVar.f.setValue(bwVar.l());
            bwVar.p(false);
            cd0 B = dl.B(bwVar, cd0Var);
            ll0 l2 = bwVar.l();
            di diVar = di.D;
            bwVar.Y();
            if (bwVar.S) {
                bwVar.k(diVar);
            } else {
                bwVar.i0();
            }
            m20.F(hz0Var.c, bwVar, hz0Var);
            m20.F(hz0Var.d, bwVar, zvVar);
            m20.F(hz0Var.e, bwVar, kvVar);
            jh.c.getClass();
            m20.F(ih.d, bwVar, l2);
            m20.C(ih.g, bwVar);
            m20.F(ih.c, bwVar, B);
            m20.F(ih.f, bwVar, Integer.valueOf(i7));
            bwVar.p(true);
            if (!bwVar.A()) {
                bwVar.V(-1259245908);
                boolean h2 = bwVar.h(hz0Var);
                Object L = bwVar.L();
                if (h2 || L == ph.a) {
                    L = new n9(15, hz0Var);
                    bwVar.f0(L);
                }
                dl.j((vu) L, bwVar);
                bwVar.p(false);
            } else {
                bwVar.V(-1259187287);
                bwVar.p(false);
            }
        } else {
            bwVar.R();
        }
        mo0 r2 = bwVar.r();
        if (r2 != null) {
            r2.d = new ez0(hz0Var, cd0Var, kvVar, i2);
        }
    }

    public static final void f(Object[] objArr, long j2, Object obj) {
        objArr[((int) j2) & (objArr.length - 1)] = obj;
    }

    public static ArrayList h(Object... objArr) {
        if (objArr.length == 0) {
            return new ArrayList();
        }
        return new ArrayList(new z7(objArr));
    }

    public static final int i(int i2, int i3) {
        return i2 << (((i3 % 10) * 3) + 1);
    }

    public static ka0 j(ka0 ka0Var) {
        ka0Var.f();
        ka0Var.g = true;
        if (ka0Var.f > 0) {
            return ka0Var;
        }
        return ka0.h;
    }

    public static final void k(AutoCloseable autoCloseable, Throwable th) {
        if (autoCloseable != null) {
            if (th == null) {
                if (autoCloseable instanceof AutoCloseable) {
                    autoCloseable.close();
                    return;
                }
                if (autoCloseable instanceof ExecutorService) {
                    x0.n((ExecutorService) autoCloseable);
                    return;
                }
                if (autoCloseable instanceof TypedArray) {
                    ((TypedArray) autoCloseable).recycle();
                    return;
                }
                if (autoCloseable instanceof MediaMetadataRetriever) {
                    ((MediaMetadataRetriever) autoCloseable).release();
                    return;
                }
                if (autoCloseable instanceof MediaDrm) {
                    ((MediaDrm) autoCloseable).release();
                    return;
                } else if (autoCloseable instanceof DrmManagerClient) {
                    ((DrmManagerClient) autoCloseable).release();
                    return;
                } else {
                    if (autoCloseable instanceof ContentProviderClient) {
                        ((ContentProviderClient) autoCloseable).release();
                        return;
                    }
                    throw new IllegalArgumentException();
                }
            }
            try {
                d3.y(autoCloseable);
            } catch (Throwable th2) {
                o20.d(th, th2);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static defpackage.hu l(android.content.Context r13) {
        /*
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 28
            r2 = 25
            if (r0 < r1) goto Le
            nl r0 = new nl
            r0.<init>(r2)
            goto L13
        Le:
            dt0 r0 = new dt0
            r0.<init>(r2)
        L13:
            android.content.pm.PackageManager r1 = r13.getPackageManager()
            java.lang.String r2 = "Package manager required to locate emoji font provider"
            defpackage.m20.k(r1, r2)
            android.content.Intent r2 = new android.content.Intent
            java.lang.String r3 = "androidx.content.action.LOAD_EMOJI_FONT"
            r2.<init>(r3)
            r3 = 0
            java.util.List r2 = r1.queryIntentContentProviders(r2, r3)
            java.util.Iterator r2 = r2.iterator()
        L2c:
            boolean r4 = r2.hasNext()
            r5 = 0
            if (r4 == 0) goto L48
            java.lang.Object r4 = r2.next()
            android.content.pm.ResolveInfo r4 = (android.content.pm.ResolveInfo) r4
            android.content.pm.ProviderInfo r4 = r4.providerInfo
            if (r4 == 0) goto L2c
            android.content.pm.ApplicationInfo r6 = r4.applicationInfo
            if (r6 == 0) goto L2c
            int r6 = r6.flags
            r7 = 1
            r6 = r6 & r7
            if (r6 != r7) goto L2c
            goto L49
        L48:
            r4 = r5
        L49:
            if (r4 != 0) goto L4d
        L4b:
            r6 = r5
            goto L7e
        L4d:
            java.lang.String r7 = r4.authority     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L77
            java.lang.String r8 = r4.packageName     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L77
            android.content.pm.Signature[] r0 = r0.h(r1, r8)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L77
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L77
            r1.<init>()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L77
            int r2 = r0.length     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L77
        L5b:
            if (r3 >= r2) goto L69
            r4 = r0[r3]     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L77
            byte[] r4 = r4.toByteArray()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L77
            r1.add(r4)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L77
            int r3 = r3 + 1
            goto L5b
        L69:
            java.util.List r10 = java.util.Collections.singletonList(r1)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L77
            fu r6 = new fu     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L77
            java.lang.String r9 = "emojicompat-emoji-font"
            r11 = 0
            r12 = 0
            r6.<init>(r7, r8, r9, r10, r11, r12)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L77
            goto L7e
        L77:
            r0 = move-exception
            java.lang.String r1 = "emoji2.text.DefaultEmojiConfig"
            android.util.Log.wtf(r1, r0)
            goto L4b
        L7e:
            if (r6 != 0) goto L81
            goto L8b
        L81:
            hu r5 = new hu
            gu r0 = new gu
            r0.<init>(r13, r6)
            r5.<init>(r0)
        L8b:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.jc0.l(android.content.Context):hu");
    }

    public static final is0 m(ee0 ee0Var) {
        ks0 ks0Var;
        LinkedHashMap linkedHashMap = ee0Var.a;
        ps0 ps0Var = (ps0) linkedHashMap.get(i);
        Bundle bundle = null;
        if (ps0Var != null) {
            w51 w51Var = (w51) linkedHashMap.get(j);
            if (w51Var != null) {
                Bundle bundle2 = (Bundle) linkedHashMap.get(k);
                String str = (String) linkedHashMap.get(n20.s);
                if (str != null) {
                    ns0 q2 = ps0Var.b().q("androidx.lifecycle.internal.SavedStateHandlesProvider");
                    if (q2 instanceof ks0) {
                        ks0Var = (ks0) q2;
                    } else {
                        ks0Var = null;
                    }
                    if (ks0Var != null) {
                        LinkedHashMap linkedHashMap2 = s(w51Var).b;
                        is0 is0Var = (is0) linkedHashMap2.get(str);
                        if (is0Var == null) {
                            ks0Var.b();
                            Bundle bundle3 = ks0Var.c;
                            if (bundle3 != null && bundle3.containsKey(str)) {
                                Bundle bundle4 = bundle3.getBundle(str);
                                if (bundle4 == null) {
                                    bundle4 = k81.l((xj0[]) Arrays.copyOf(new xj0[0], 0));
                                }
                                bundle3.remove(str);
                                if (bundle3.isEmpty()) {
                                    ks0Var.c = null;
                                }
                                bundle = bundle4;
                            }
                            is0 p2 = n30.p(bundle, bundle2);
                            linkedHashMap2.put(str, p2);
                            return p2;
                        }
                        return is0Var;
                    }
                    v7.o("enableSavedStateHandles() wasn't called prior to createSavedStateHandle() call");
                    return null;
                }
                v7.m("CreationExtras must have a value by `VIEW_MODEL_KEY`");
                return null;
            }
            v7.m("CreationExtras must have a value by `VIEW_MODEL_STORE_OWNER_KEY`");
            return null;
        }
        v7.m("CreationExtras must have a value by `SAVED_STATE_REGISTRY_OWNER_KEY`");
        return null;
    }

    public static cd0 n(cd0 cd0Var, kl klVar) {
        return cd0Var.b(new dp(klVar));
    }

    public static p10 o(int i2, long j2) {
        long j3 = i2;
        long j4 = j3 / 1000000000;
        if ((j3 ^ 1000000000) < 0 && j4 * 1000000000 != j3) {
            j4--;
        }
        long j5 = j2 + j4;
        if ((j2 ^ j5) < 0 && (j4 ^ j2) >= 0) {
            if (j2 > 0) {
                return p10.h;
            }
            return p10.g;
        }
        if (j5 < -31557014167219200L) {
            return p10.g;
        }
        if (j5 > 31556889864403199L) {
            return p10.h;
        }
        long j6 = j3 % 1000000000;
        return new p10((int) (j6 + ((((j6 ^ 1000000000) & ((-j6) | j6)) >> 63) & 1000000000)), j5);
    }

    public static wj p(wj wjVar, xj xjVar) {
        xjVar.getClass();
        if (o20.e(wjVar.getKey(), xjVar)) {
            return wjVar;
        }
        return null;
    }

    public static int q(List list) {
        list.getClass();
        return list.size() - 1;
    }

    public static hr0 r(Display display, int i2) {
        RoundedCorner j2;
        int i3;
        if (Build.VERSION.SDK_INT >= 31 && (j2 = l4.j(display, i2)) != null) {
            int b2 = l4.b(j2);
            if (b2 != 0) {
                i3 = 1;
                if (b2 != 1) {
                    i3 = 2;
                    if (b2 != 2) {
                        i3 = 3;
                        if (b2 != 3) {
                            throw new IllegalArgumentException("Invalid position: " + b2);
                        }
                    }
                }
            } else {
                i3 = 0;
            }
            return new hr0(i3, l4.C(j2), l4.c(j2));
        }
        return null;
    }

    public static final ls0 s(w51 w51Var) {
        nk nkVar;
        Bundle bundle;
        rt rtVar = new rt(26);
        if (w51Var instanceof cy) {
            cg cgVar = (cg) ((cy) w51Var);
            nkVar = new ee0(mk.b);
            Application application = cgVar.getApplication();
            LinkedHashMap linkedHashMap = nkVar.a;
            if (application != null) {
                linkedHashMap.put(u51.j, cgVar.getApplication());
            }
            linkedHashMap.put(i, cgVar);
            linkedHashMap.put(j, cgVar);
            Intent intent = cgVar.getIntent();
            if (intent != null) {
                bundle = intent.getExtras();
            } else {
                bundle = null;
            }
            if (bundle != null) {
                linkedHashMap.put(k, bundle);
            }
        } else {
            nkVar = mk.b;
        }
        nkVar.getClass();
        return (ls0) new e3(((cg) w51Var).c(), rtVar, nkVar).o(fp0.a(ls0.class), "androidx.lifecycle.internal.SavedStateHandlesVM");
    }

    public static final void t(bw bwVar, kv kvVar) {
        kvVar.getClass();
        f31.n(2, kvVar);
        kvVar.d(bwVar, 1);
    }

    public static final boolean u(int i2, int i3, long j2) {
        int j3 = si.j(j2);
        if (i2 <= si.h(j2) && j3 <= i2) {
            int i4 = si.i(j2);
            if (i3 <= si.g(j2) && i4 <= i3) {
                return true;
            }
            return false;
        }
        return false;
    }

    public static List v(Object obj) {
        List singletonList = Collections.singletonList(obj);
        singletonList.getClass();
        return singletonList;
    }

    public static List w(Object... objArr) {
        if (objArr.length > 0) {
            List asList = Arrays.asList(objArr);
            asList.getClass();
            return asList;
        }
        return er.e;
    }

    public static yj x(wj wjVar, xj xjVar) {
        xjVar.getClass();
        if (o20.e(wjVar.getKey(), xjVar)) {
            return cr.e;
        }
        return wjVar;
    }

    /* JADX WARN: Removed duplicated region for block: B:138:0x024a  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x0258  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final long y(int r32, int r33, java.lang.String r34) {
        /*
            Method dump skipped, instructions count: 798
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.jc0.y(int, int, java.lang.String):long");
    }

    public static yj z(wj wjVar, yj yjVar) {
        yjVar.getClass();
        if (yjVar == cr.e) {
            return wjVar;
        }
        return (yj) yjVar.n(new w4(3, (byte) 0), wjVar);
    }

    public abstract void g(float f2, long j2, r5 r5Var);
}
