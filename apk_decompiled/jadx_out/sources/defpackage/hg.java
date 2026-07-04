package defpackage;

import android.view.View;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class hg extends z30 implements lv {
    public static final hg g;
    public static final hg h;
    public static final hg i;
    public static final hg j;
    public static final hg k;
    public static final hg l;
    public static final hg m;
    public static final hg n;
    public final /* synthetic */ int f;

    static {
        int i2 = 3;
        g = new hg(i2, 0);
        h = new hg(i2, 1);
        i = new hg(i2, 2);
        j = new hg(i2, 3);
        k = new hg(i2, 4);
        l = new hg(i2, 5);
        m = new hg(i2, 6);
        n = new hg(i2, 7);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ hg(int i2, int i3) {
        super(i2);
        this.f = i3;
    }

    @Override // defpackage.lv
    public final Object c(Object obj, Object obj2, Object obj3) {
        int i2 = this.f;
        int i3 = 21;
        dt0 dt0Var = ph.a;
        x31 x31Var = x31.a;
        boolean z = false;
        switch (i2) {
            case 0:
                bw bwVar = (bw) obj2;
                int intValue = ((Number) obj3).intValue();
                ((qr0) obj).getClass();
                if ((intValue & 17) != 16) {
                    z = true;
                }
                if (bwVar.O(intValue & 1, z)) {
                    dl.b("Pick an image", dl.D(zc0.a, 8.0f), new r11(se.c, d20.A(4294967296L, 16.0f), null, 16777212), 0, false, 0, 0, null, bwVar, 438, 1016);
                } else {
                    bwVar.R();
                }
                return x31Var;
            case 1:
                bw bwVar2 = (bw) obj2;
                int intValue2 = ((Number) obj3).intValue();
                ((qr0) obj).getClass();
                if ((intValue2 & 17) != 16) {
                    z = true;
                }
                if (bwVar2.O(intValue2 & 1, z)) {
                    dl.b("Tinted Liquid Button", null, new r11(se.c, d20.A(4294967296L, 15.0f), null, 16777212), 0, false, 0, 0, null, bwVar2, 390, 1018);
                } else {
                    bwVar2.R();
                }
                return x31Var;
            case 2:
                bw bwVar3 = (bw) obj2;
                int intValue3 = ((Number) obj3).intValue();
                ((qr0) obj).getClass();
                if ((intValue3 & 17) != 16) {
                    z = true;
                }
                if (bwVar3.O(intValue3 & 1, z)) {
                    dl.b("Tinted Liquid Button", null, new r11(se.c, d20.A(4294967296L, 15.0f), null, 16777212), 0, false, 0, 0, null, bwVar3, 390, 1018);
                } else {
                    bwVar3.R();
                }
                return x31Var;
            case 3:
                bw bwVar4 = (bw) obj2;
                int intValue4 = ((Number) obj3).intValue();
                ((qr0) obj).getClass();
                if ((intValue4 & 17) != 16) {
                    z = true;
                }
                if (bwVar4.O(intValue4 & 1, z)) {
                    dl.b("Transparent Liquid Button", null, new r11(se.b, d20.A(4294967296L, 15.0f), null, 16777212), 0, false, 0, 0, null, bwVar4, 390, 1018);
                } else {
                    bwVar4.R();
                }
                return x31Var;
            case 4:
                bw bwVar5 = (bw) obj2;
                int intValue5 = ((Number) obj3).intValue();
                ((qr0) obj).getClass();
                if ((intValue5 & 17) != 16) {
                    z = true;
                }
                if (bwVar5.O(intValue5 & 1, z)) {
                    dl.b("Surface Liquid Button", null, new r11(se.b, d20.A(4294967296L, 15.0f), null, 16777212), 0, false, 0, 0, null, bwVar5, 390, 1018);
                } else {
                    bwVar5.R();
                }
                return x31Var;
            case 5:
                bw bwVar6 = (bw) obj2;
                int intValue6 = ((Number) obj3).intValue();
                ((qr0) obj).getClass();
                if ((intValue6 & 17) != 16) {
                    z = true;
                }
                if (bwVar6.O(intValue6 & 1, z)) {
                    dl.b("Reset", null, new r11(se.c, d20.A(4294967296L, 15.0f), null, 16777212), 0, false, 0, 0, null, bwVar6, 390, 1018);
                } else {
                    bwVar6.R();
                }
                return x31Var;
            case 6:
                bw bwVar7 = (bw) obj2;
                int intValue7 = ((Number) obj3).intValue();
                ((t50) obj).getClass();
                if ((intValue7 & 17) != 16) {
                    z = true;
                }
                if (bwVar7.O(intValue7 & 1, z)) {
                    WeakHashMap weakHashMap = l71.v;
                    View view = (View) bwVar7.j(p4.e);
                    l71 g2 = ey0.g(view);
                    boolean h2 = bwVar7.h(g2) | bwVar7.h(view);
                    Object L = bwVar7.L();
                    if (h2 || L == dt0Var) {
                        L = new c(i3, g2, view);
                        bwVar7.f0(L);
                    }
                    dl.f(g2, (gv) L, bwVar7);
                    t20.e(bwVar7, f31.Y(g2.g));
                } else {
                    bwVar7.R();
                }
                return x31Var;
            default:
                bw bwVar8 = (bw) obj2;
                int intValue8 = ((Number) obj3).intValue();
                ((t50) obj).getClass();
                if ((intValue8 & 17) != 16) {
                    z = true;
                }
                if (bwVar8.O(intValue8 & 1, z)) {
                    WeakHashMap weakHashMap2 = l71.v;
                    View view2 = (View) bwVar8.j(p4.e);
                    l71 g3 = ey0.g(view2);
                    boolean h3 = bwVar8.h(g3) | bwVar8.h(view2);
                    Object L2 = bwVar8.L();
                    if (h3 || L2 == dt0Var) {
                        L2 = new c(i3, g3, view2);
                        bwVar8.f0(L2);
                    }
                    dl.f(g3, (gv) L2, bwVar8);
                    t20.e(bwVar8, f31.X(g3.g));
                } else {
                    bwVar8.R();
                }
                return x31Var;
        }
    }
}
