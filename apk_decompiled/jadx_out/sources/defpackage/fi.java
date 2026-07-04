package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class fi {
    public static final qy0 a = new do0(n2.y);
    public static final qy0 b = new do0(n2.z);
    public static final qy0 c = new do0(n2.B);
    public static final qy0 d = new do0(n2.A);
    public static final qy0 e = new do0(n2.D);
    public static final qy0 f = new do0(n2.C);
    public static final qy0 g = new do0(n2.J);
    public static final qy0 h = new do0(n2.F);
    public static final qy0 i = new do0(n2.G);
    public static final qy0 j = new do0(n2.I);
    public static final qy0 k = new do0(n2.H);
    public static final qy0 l = new do0(di.g);
    public static final qy0 m = new do0(di.h);
    public static final qy0 n = new do0(di.i);
    public static final qy0 o = new do0(di.k);
    public static final qy0 p;
    public static final qy0 q;
    public static final qy0 r;
    public static final qy0 s;
    public static final qy0 t;
    public static final qy0 u;
    public static final qy0 v;
    public static final gi w;

    /* JADX WARN: Type inference failed for: r1v0, types: [do0, qy0] */
    /* JADX WARN: Type inference failed for: r1v1, types: [do0, qy0] */
    /* JADX WARN: Type inference failed for: r1v10, types: [do0, qy0] */
    /* JADX WARN: Type inference failed for: r1v11, types: [do0, qy0] */
    /* JADX WARN: Type inference failed for: r1v12, types: [do0, qy0] */
    /* JADX WARN: Type inference failed for: r1v13, types: [do0, qy0] */
    /* JADX WARN: Type inference failed for: r1v14, types: [do0, qy0] */
    /* JADX WARN: Type inference failed for: r1v16, types: [do0, qy0] */
    /* JADX WARN: Type inference failed for: r1v17, types: [do0, qy0] */
    /* JADX WARN: Type inference failed for: r1v18, types: [do0, qy0] */
    /* JADX WARN: Type inference failed for: r1v19, types: [do0, qy0] */
    /* JADX WARN: Type inference failed for: r1v2, types: [do0, qy0] */
    /* JADX WARN: Type inference failed for: r1v20, types: [do0, qy0] */
    /* JADX WARN: Type inference failed for: r1v21, types: [do0, qy0] */
    /* JADX WARN: Type inference failed for: r1v22, types: [do0, qy0] */
    /* JADX WARN: Type inference failed for: r1v3, types: [do0, qy0] */
    /* JADX WARN: Type inference failed for: r1v4, types: [do0, qy0] */
    /* JADX WARN: Type inference failed for: r1v5, types: [do0, qy0] */
    /* JADX WARN: Type inference failed for: r1v6, types: [do0, qy0] */
    /* JADX WARN: Type inference failed for: r1v7, types: [do0, qy0] */
    /* JADX WARN: Type inference failed for: r1v8, types: [do0, qy0] */
    /* JADX WARN: Type inference failed for: r1v9, types: [do0, qy0] */
    static {
        n30.A(new c2(5));
        p = new do0(di.n);
        q = new do0(di.m);
        r = new do0(di.o);
        s = new do0(di.p);
        t = new do0(di.q);
        u = new do0(di.r);
        v = new do0(di.j);
        w = new gi(di.l);
        n30.A(n2.E);
    }

    public static final void a(mj0 mj0Var, dt0 dt0Var, kv kvVar, bw bwVar, int i2) {
        int i3;
        int i4;
        int i5;
        boolean z;
        bwVar.W(1925803616);
        if (bwVar.f(mj0Var)) {
            i3 = 4;
        } else {
            i3 = 2;
        }
        int i6 = i3 | i2;
        if (bwVar.f(dt0Var)) {
            i4 = 32;
        } else {
            i4 = 16;
        }
        int i7 = i6 | i4;
        if (bwVar.h(kvVar)) {
            i5 = 256;
        } else {
            i5 = 128;
        }
        int i8 = i7 | i5;
        if ((i8 & 147) != 146) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i8 & 1, z)) {
            b4 b4Var = (b4) mj0Var;
            eo0 a2 = a.a(b4Var.m3getAccessibilityManager());
            eo0 a3 = b.a(b4Var.getAutofill());
            eo0 a4 = d.a(b4Var.getAutofillManager());
            eo0 a5 = c.a(b4Var.getAutofillTree());
            eo0 a6 = e.a(b4Var.getClipboardManager());
            eo0 a7 = f.a(b4Var.getClipboard());
            eo0 a8 = h.a(b4Var.getDensity());
            eo0 a9 = i.a(b4Var.getFocusOwner());
            eo0 a10 = j.a(b4Var.getFontLoader());
            a10.f = false;
            eo0 a11 = k.a(b4Var.getFontFamilyResolver());
            a11.f = false;
            o20.b(new eo0[]{a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, l.a(b4Var.getHapticFeedBack()), m.a(b4Var.getInputModeManager()), n.a(b4Var.getLayoutDirection()), p.a(b4Var.getTextInputService()), q.a(b4Var.getSoftwareKeyboardController()), r.a(b4Var.getTextToolbar()), s.a(dt0Var), t.a(b4Var.getViewConfiguration()), u.a(b4Var.getWindowInfo()), v.a(b4Var.getPointerIconService()), g.a(b4Var.getGraphicsContext()), ra0.a.a(b4Var.getRetainedValuesStore()), o.a(b4Var.getLocaleList())}, kvVar, bwVar, ((i8 >> 3) & 112) | 8);
        } else {
            bwVar.R();
        }
        mo0 r2 = bwVar.r();
        if (r2 != null) {
            r2.d = new ei(mj0Var, dt0Var, kvVar, i2);
        }
    }

    public static final void b(String str) {
        throw new IllegalStateException(("CompositionLocal " + str + " not present").toString());
    }
}
