package defpackage;

import java.util.Collections;
import java.util.Set;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class di extends z30 implements vu {
    public static final di A;
    public static final di B;
    public static final di C;
    public static final di D;
    public static final di E;
    public static final di F;
    public static final di G;
    public static final di H;
    public static final di I;
    public static final di J;
    public static final di g;
    public static final di h;
    public static final di i;
    public static final di j;
    public static final di k;
    public static final di l;
    public static final di m;
    public static final di n;
    public static final di o;
    public static final di p;
    public static final di q;
    public static final di r;
    public static final di s;
    public static final di t;
    public static final di u;
    public static final di v;
    public static final di w;
    public static final di x;
    public static final di y;
    public static final di z;
    public final /* synthetic */ int f;

    static {
        int i2 = 0;
        g = new di(i2, 0);
        h = new di(i2, 1);
        i = new di(i2, 2);
        j = new di(i2, 3);
        k = new di(i2, 4);
        l = new di(i2, 5);
        m = new di(i2, 6);
        n = new di(i2, 7);
        o = new di(i2, 8);
        p = new di(i2, 9);
        q = new di(i2, 10);
        r = new di(i2, 11);
        s = new di(i2, 12);
        t = new di(i2, 13);
        u = new di(i2, 14);
        v = new di(i2, 15);
        w = new di(i2, 16);
        x = new di(i2, 17);
        y = new di(i2, 18);
        z = new di(i2, 19);
        A = new di(i2, 20);
        B = new di(i2, 21);
        C = new di(i2, 22);
        D = new di(i2, 23);
        E = new di(i2, 24);
        F = new di(i2, 25);
        G = new di(i2, 26);
        H = new di(i2, 27);
        I = new di(i2, 28);
        J = new di(i2, 29);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ di(int i2, int i3) {
        super(i2);
        this.f = i3;
    }

    @Override // defpackage.vu
    public final Object a() {
        int i2 = this.f;
        x31 x31Var = x31.a;
        switch (i2) {
            case 0:
                fi.b("LocalHapticFeedback");
                throw null;
            case 1:
                fi.b("LocalInputManager");
                throw null;
            case 2:
                fi.b("LocalLayoutDirection");
                throw null;
            case 3:
                return null;
            case 4:
                fi.b("LocalProvidableLocaleList");
                throw null;
            case 5:
                return Boolean.FALSE;
            case 6:
            case 7:
                return null;
            case 8:
                fi.b("LocalTextToolbar");
                throw null;
            case 9:
                fi.b("LocalUriHandler");
                throw null;
            case 10:
                fi.b("LocalViewConfiguration");
                throw null;
            case 11:
                fi.b("LocalWindowInfo");
                throw null;
            case 12:
                return new jr0(48.0f);
            case 13:
                return dy.g;
            case 14:
            case 15:
                return x31Var;
            case 16:
                Set singleton = Collections.singleton(new fq0("composeResources/glass.app.generated.resources/drawable/clock_sdf.webp"));
                singleton.getClass();
                return new zp("drawable:clock_sdf", singleton);
            case 17:
                Set singleton2 = Collections.singleton(new fq0("composeResources/glass.app.generated.resources/drawable/wallpaper_light.webp"));
                singleton2.getClass();
                return new zp("drawable:wallpaper_light", singleton2);
            case 18:
                return Boolean.FALSE;
            case 19:
                return dy.g;
            case 20:
                return new jr0(32.0f);
            case 21:
                return dy.g;
            case 22:
                return Boolean.FALSE;
            case 23:
                return new z40(3);
            case 24:
                return Float.valueOf(1.0f);
            case 25:
                return E;
            case 26:
                return new ad();
            case 27:
                return new ad();
            case 28:
                return new ad();
            default:
                return new ad();
        }
    }
}
