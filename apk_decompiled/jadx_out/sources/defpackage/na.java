package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class na extends z30 implements lv {
    public final /* synthetic */ int f;
    public final /* synthetic */ fk0 g;
    public final /* synthetic */ q41 h;
    public final /* synthetic */ da i;
    public final /* synthetic */ long j;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ na(fk0 fk0Var, q41 q41Var, da daVar, long j, int i) {
        super(3);
        this.f = i;
        this.g = fk0Var;
        this.h = q41Var;
        this.i = daVar;
        this.j = j;
    }

    @Override // defpackage.lv
    public final Object c(Object obj, Object obj2, Object obj3) {
        boolean z;
        int i = this.f;
        x31 x31Var = x31.a;
        dt0 dt0Var = ph.a;
        fk0 fk0Var = this.g;
        int i2 = 2;
        boolean z2 = true;
        int i3 = 0;
        switch (i) {
            case 0:
                qr0 qr0Var = (qr0) obj;
                bw bwVar = (bw) obj2;
                int intValue = ((Number) obj3).intValue();
                qr0Var.getClass();
                if ((intValue & 6) == 0) {
                    if (bwVar.f(qr0Var)) {
                        i2 = 4;
                    }
                    intValue |= i2;
                }
                if ((intValue & 19) == 18) {
                    z2 = false;
                }
                if (bwVar.O(intValue & 1, z2)) {
                    for (int i4 = 0; i4 < 3; i4++) {
                        boolean f = bwVar.f(fk0Var) | bwVar.d(i4);
                        Object L = bwVar.L();
                        if (f || L == dt0Var) {
                            L = new la(i4, fk0Var, 0);
                            bwVar.f0(L);
                        }
                        h90.a(qr0Var, (vu) L, null, jc0.C(-1550555203, new ma(this.h, this.i, i4, this.j, 0), bwVar), bwVar, (intValue & 14) | 3072);
                    }
                } else {
                    bwVar.R();
                }
                return x31Var;
            default:
                qr0 qr0Var2 = (qr0) obj;
                bw bwVar2 = (bw) obj2;
                int intValue2 = ((Number) obj3).intValue();
                qr0Var2.getClass();
                if ((intValue2 & 6) == 0) {
                    if (bwVar2.f(qr0Var2)) {
                        i2 = 4;
                    }
                    intValue2 |= i2;
                }
                if ((intValue2 & 19) != 18) {
                    z = true;
                } else {
                    z = false;
                }
                if (bwVar2.O(intValue2 & 1, z)) {
                    while (i3 < 4) {
                        boolean f2 = bwVar2.f(fk0Var) | bwVar2.d(i3);
                        Object L2 = bwVar2.L();
                        if (f2 || L2 == dt0Var) {
                            L2 = new la(i3, fk0Var, 1);
                            bwVar2.f0(L2);
                        }
                        int i5 = i3;
                        h90.a(qr0Var2, (vu) L2, null, jc0.C(-334929676, new ma(this.h, this.i, i5, this.j, 1), bwVar2), bwVar2, (intValue2 & 14) | 3072);
                        i3 = i5 + 1;
                    }
                } else {
                    bwVar2.R();
                }
                return x31Var;
        }
    }
}
