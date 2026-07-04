package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class uy implements zv0 {
    public static final uy b = new uy(0);
    public static final uy c = new uy(1);
    public final /* synthetic */ int a;

    public /* synthetic */ uy(int i) {
        this.a = i;
    }

    @Override // defpackage.zv0
    public final g30 b(long j, m40 m40Var, mm mmVar) {
        switch (this.a) {
            case 0:
                float S = mmVar.S(30.0f);
                return new gj0(new wo0(0.0f, -S, Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j & 4294967295L)) + S));
            case 1:
                float S2 = mmVar.S(30.0f);
                return new gj0(new wo0(-S2, 0.0f, Float.intBitsToFloat((int) (j >> 32)) + S2, Float.intBitsToFloat((int) (j & 4294967295L))));
            default:
                return new gj0(t20.c(0L, j));
        }
    }

    public String toString() {
        switch (this.a) {
            case 2:
                return "RectangleShape";
            default:
                return super.toString();
        }
    }
}
