package defpackage;

import android.os.Looper;
import android.view.Choreographer;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class n2 extends z30 implements vu {
    public static final n2 A;
    public static final n2 B;
    public static final n2 C;
    public static final n2 D;
    public static final n2 E;
    public static final n2 F;
    public static final n2 G;
    public static final n2 H;
    public static final n2 I;
    public static final n2 J;
    public static final n2 g;
    public static final n2 h;
    public static final n2 i;
    public static final n2 j;
    public static final n2 k;
    public static final n2 l;
    public static final n2 m;
    public static final n2 n;
    public static final n2 o;
    public static final n2 p;
    public static final n2 q;
    public static final n2 r;
    public static final n2 s;
    public static final n2 t;
    public static final n2 u;
    public static final n2 v;
    public static final n2 w;
    public static final n2 x;
    public static final n2 y;
    public static final n2 z;
    public final /* synthetic */ int f;

    static {
        int i2 = 0;
        g = new n2(i2, 0);
        h = new n2(i2, 1);
        i = new n2(i2, 2);
        j = new n2(i2, 3);
        k = new n2(i2, 4);
        l = new n2(i2, 5);
        m = new n2(i2, 6);
        n = new n2(i2, 7);
        o = new n2(i2, 8);
        p = new n2(i2, 9);
        q = new n2(i2, 10);
        r = new n2(i2, 11);
        s = new n2(i2, 12);
        t = new n2(i2, 13);
        u = new n2(i2, 14);
        v = new n2(i2, 15);
        w = new n2(i2, 16);
        x = new n2(i2, 17);
        y = new n2(i2, 18);
        z = new n2(i2, 19);
        A = new n2(i2, 20);
        B = new n2(i2, 21);
        C = new n2(i2, 22);
        D = new n2(i2, 23);
        E = new n2(i2, 24);
        F = new n2(i2, 25);
        G = new n2(i2, 26);
        H = new n2(i2, 27);
        I = new n2(i2, 28);
        J = new n2(i2, 29);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ n2(int i2, int i3) {
        super(i2);
        this.f = i3;
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [sz0, kv] */
    @Override // defpackage.vu
    public final Object a() {
        Choreographer choreographer;
        int i2 = this.f;
        x31 x31Var = x31.a;
        switch (i2) {
            case 0:
                return new jr0(24.0f);
            case 1:
                return dy.g;
            case 2:
                p4.a("LocalConfiguration");
                throw null;
            case 3:
                p4.a("LocalContext");
                throw null;
            case 4:
                p4.a("LocalImageVectorCache");
                throw null;
            case 5:
                p4.a("LocalResourceIdCache");
                throw null;
            case 6:
                p4.a("LocalView");
                throw null;
            case 7:
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    choreographer = Choreographer.getInstance();
                } else {
                    bm bmVar = mn.a;
                    choreographer = (Choreographer) f31.M(yb0.a, new sz0(2, null));
                }
                n6 n6Var = new n6(choreographer, o20.k(Looper.getMainLooper()));
                return jc0.z(n6Var, n6Var.p);
            case 8:
                return new fk0(0);
            case 9:
                return new fk0(0);
            case 10:
            case 11:
            case 12:
            case 13:
                return x31Var;
            case 14:
                return new jr0(32.0f);
            case 15:
                return zo0.a;
            case 16:
                return n30.B(bd.e);
            case 17:
                return new jr0(32.0f);
            case 18:
            case 19:
                return null;
            case 20:
                fi.b("LocalAutofillManager");
                throw null;
            case 21:
                fi.b("LocalAutofillTree");
                throw null;
            case 22:
                fi.b("LocalClipboard");
                throw null;
            case 23:
                fi.b("LocalClipboardManager");
                throw null;
            case 24:
                return Boolean.TRUE;
            case 25:
                fi.b("LocalDensity");
                throw null;
            case 26:
                fi.b("LocalFocusManager");
                throw null;
            case 27:
                fi.b("LocalFontFamilyResolver");
                throw null;
            case 28:
                fi.b("LocalFontLoader");
                throw null;
            default:
                fi.b("LocalGraphicsContext");
                throw null;
        }
    }
}
