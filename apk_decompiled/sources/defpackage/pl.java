package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class pl {
    public static final hm a;

    static {
        String str;
        boolean z;
        hm hmVar;
        int i = d01.a;
        try {
            str = System.getProperty("kotlinx.coroutines.main.delay");
        } catch (SecurityException unused) {
            str = null;
        }
        if (str != null) {
            z = Boolean.parseBoolean(str);
        } else {
            z = false;
        }
        if (!z) {
            hmVar = ol.p;
        } else {
            bm bmVar = mn.a;
            xx xxVar = yb0.a;
            xx xxVar2 = xxVar.j;
            hmVar = xxVar;
            if (xxVar == null) {
                hmVar = ol.p;
            }
        }
        a = hmVar;
    }
}
