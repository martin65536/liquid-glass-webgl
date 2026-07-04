package defpackage;

import android.view.ViewStructure;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class mg extends z30 implements mv {
    public final /* synthetic */ int f;
    public final /* synthetic */ Object g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ mg(int i, Object obj) {
        super(4);
        this.f = i;
        this.g = obj;
    }

    @Override // defpackage.mv
    public final Object h(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean z;
        int i = this.f;
        x31 x31Var = x31.a;
        Object obj5 = this.g;
        switch (i) {
            case 0:
                ((Number) obj2).intValue();
                bw bwVar = (bw) obj3;
                int intValue = ((Number) obj4).intValue();
                ((t50) obj).getClass();
                if ((intValue & 129) != 128) {
                    z = true;
                } else {
                    z = false;
                }
                if (bwVar.O(intValue & 1, z)) {
                    c40 c40Var = (c40) obj5;
                    Object L = bwVar.L();
                    dt0 dt0Var = ph.a;
                    if (L == dt0Var) {
                        L = n2.u;
                        bwVar.f0(L);
                    }
                    vu vuVar = (vu) L;
                    Object L2 = bwVar.L();
                    if (L2 == dt0Var) {
                        L2 = w3.k;
                        bwVar.f0(L2);
                    }
                    ya.a(k81.r(k81.z(f31.s(zc0.a, c40Var, vuVar, (gv) L2, null, null, null, null, null, null, null, 4088), 160.0f), 1.0f), bwVar, 0);
                } else {
                    bwVar.R();
                }
                return x31Var;
            default:
                int intValue2 = ((Number) obj).intValue();
                int intValue3 = ((Number) obj2).intValue();
                ((ViewStructure) obj5).setDimens(intValue2, intValue3, 0, 0, ((Number) obj3).intValue() - intValue2, ((Number) obj4).intValue() - intValue3);
                return x31Var;
        }
    }
}
