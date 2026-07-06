package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class oa extends z30 implements kv {
    public final /* synthetic */ int f;
    public final /* synthetic */ c40 g;
    public final /* synthetic */ q41 h;
    public final /* synthetic */ da i;
    public final /* synthetic */ long j;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ oa(c40 c40Var, q41 q41Var, da daVar, long j, int i) {
        super(2);
        this.f = i;
        this.g = c40Var;
        this.h = q41Var;
        this.i = daVar;
        this.j = j;
    }

    @Override // defpackage.kv
    public final Object d(Object obj, Object obj2) {
        boolean z;
        boolean z2;
        int i = this.f;
        x31 x31Var = x31.a;
        zc0 zc0Var = zc0.a;
        Object obj3 = ph.a;
        switch (i) {
            case 0:
                bw bwVar = (bw) obj;
                int intValue = ((Number) obj2).intValue();
                if ((intValue & 3) != 2) {
                    z = true;
                } else {
                    z = false;
                }
                if (bwVar.O(intValue & 1, z)) {
                    Object[] objArr = new Object[0];
                    Object L = bwVar.L();
                    if (L == obj3) {
                        L = n2.o;
                        bwVar.f0(L);
                    }
                    fk0 fk0Var = (fk0) y20.r(objArr, (vu) L, bwVar);
                    boolean f = bwVar.f(fk0Var);
                    Object L2 = bwVar.L();
                    if (f || L2 == obj3) {
                        L2 = new ja(fk0Var, 0);
                        bwVar.f0(L2);
                    }
                    vu vuVar = (vu) L2;
                    boolean f2 = bwVar.f(fk0Var);
                    Object L3 = bwVar.L();
                    if (f2 || L3 == obj3) {
                        L3 = new ka(fk0Var, 0);
                        bwVar.f0(L3);
                    }
                    y20.c(vuVar, (gv) L3, this.g, 3, dl.D(zc0Var, 36.0f), jc0.C(-1950560706, new na(fk0Var, this.h, this.i, this.j, 0), bwVar), bwVar, 224256);
                } else {
                    bwVar.R();
                }
                return x31Var;
            default:
                bw bwVar2 = (bw) obj;
                int intValue2 = ((Number) obj2).intValue();
                if ((intValue2 & 3) != 2) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (bwVar2.O(intValue2 & 1, z2)) {
                    Object[] objArr2 = new Object[0];
                    Object L4 = bwVar2.L();
                    if (L4 == obj3) {
                        L4 = n2.p;
                        bwVar2.f0(L4);
                    }
                    fk0 fk0Var2 = (fk0) y20.r(objArr2, (vu) L4, bwVar2);
                    boolean f3 = bwVar2.f(fk0Var2);
                    Object L5 = bwVar2.L();
                    if (f3 || L5 == obj3) {
                        L5 = new ja(fk0Var2, 1);
                        bwVar2.f0(L5);
                    }
                    vu vuVar2 = (vu) L5;
                    boolean f4 = bwVar2.f(fk0Var2);
                    Object L6 = bwVar2.L();
                    if (f4 || L6 == obj3) {
                        L6 = new ka(fk0Var2, 1);
                        bwVar2.f0(L6);
                    }
                    y20.c(vuVar2, (gv) L6, this.g, 4, dl.D(zc0Var, 36.0f), jc0.C(1806838581, new na(fk0Var2, this.h, this.i, this.j, 1), bwVar2), bwVar2, 224256);
                } else {
                    bwVar2.R();
                }
                return x31Var;
        }
    }
}
