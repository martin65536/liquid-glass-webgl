package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class qw extends z30 implements lv {
    public final /* synthetic */ int f;
    public final /* synthetic */ Object g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ qw(int i, Object obj) {
        super(3);
        this.f = i;
        this.g = obj;
    }

    @Override // defpackage.lv
    public final Object c(Object obj, Object obj2, Object obj3) {
        boolean z;
        String str;
        int i = this.f;
        Object obj4 = this.g;
        switch (i) {
            case 0:
                bw bwVar = (bw) obj2;
                int intValue = ((Number) obj3).intValue();
                ((qr0) obj).getClass();
                if ((intValue & 17) != 16) {
                    z = true;
                } else {
                    z = false;
                }
                if (bwVar.O(intValue & 1, z)) {
                    if (((Boolean) ((af0) obj4).getValue()).booleanValue()) {
                        str = "🔽";
                    } else {
                        str = "🔼";
                    }
                    dl.b(str, null, new r11(se.c, d20.A(4294967296L, 15.0f), null, 16777212), 0, false, 0, 0, null, bwVar, 384, 1018);
                } else {
                    bwVar.R();
                }
                return x31.a;
            default:
                rc0 rc0Var = (rc0) obj;
                kc0 kc0Var = (kc0) obj2;
                long j = ((si) obj3).a;
                rc0Var.getClass();
                kc0Var.getClass();
                em0 v = kc0Var.v(j);
                return rc0Var.e0(Math.round(((al) obj4).c() * si.h(j)), v.f, fr.e, new p3(v, 3));
        }
    }
}
