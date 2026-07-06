package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ma extends z30 implements lv {
    public final /* synthetic */ int f;
    public final /* synthetic */ q41 g;
    public final /* synthetic */ da h;
    public final /* synthetic */ int i;
    public final /* synthetic */ long j;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ ma(q41 q41Var, da daVar, int i, long j, int i2) {
        super(3);
        this.f = i2;
        this.g = q41Var;
        this.h = daVar;
        this.i = i;
        this.j = j;
    }

    @Override // defpackage.lv
    public final Object c(Object obj, Object obj2, Object obj3) {
        boolean z;
        boolean z2;
        int i = this.f;
        x31 x31Var = x31.a;
        int i2 = this.i;
        zc0 zc0Var = zc0.a;
        switch (i) {
            case 0:
                bw bwVar = (bw) obj2;
                int intValue = ((Number) obj3).intValue();
                ((ff) obj).getClass();
                if ((intValue & 17) != 16) {
                    z = true;
                } else {
                    z = false;
                }
                if (bwVar.O(intValue & 1, z)) {
                    ya.a(n20.G(k81.J(zc0Var, 28.0f), this.g, null, 0.0f, this.h, 30), bwVar, 0);
                    dl.b("Tab " + (i2 + 1), null, new r11(this.j, d20.A(4294967296L, 12.0f), null, 16777212), 0, false, 0, 0, null, bwVar, 0, 1018);
                } else {
                    bwVar.R();
                }
                return x31Var;
            default:
                bw bwVar2 = (bw) obj2;
                int intValue2 = ((Number) obj3).intValue();
                ((ff) obj).getClass();
                if ((intValue2 & 17) != 16) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (bwVar2.O(intValue2 & 1, z2)) {
                    ya.a(n20.G(k81.J(zc0Var, 28.0f), this.g, null, 0.0f, this.h, 30), bwVar2, 0);
                    dl.b("Tab " + (i2 + 1), null, new r11(this.j, d20.A(4294967296L, 12.0f), null, 16777212), 0, false, 0, 0, null, bwVar2, 0, 1018);
                } else {
                    bwVar2.R();
                }
                return x31Var;
        }
    }
}
