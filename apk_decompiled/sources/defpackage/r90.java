package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class r90 extends z30 implements vu {
    public final /* synthetic */ int f;
    public final /* synthetic */ Object g;
    public final /* synthetic */ Object h;
    public final /* synthetic */ Object i;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ r90(Object obj, Object obj2, Object obj3, int i) {
        super(0);
        this.f = i;
        this.g = obj;
        this.h = obj2;
        this.i = obj3;
    }

    @Override // defpackage.vu
    public final Object a() {
        int i = this.f;
        Object obj = this.i;
        Object obj2 = this.h;
        Object obj3 = this.g;
        switch (i) {
            case 0:
                float floatValue = ((Number) ((y6) obj3).d()).floatValue() / si.h(((gb) obj2).b);
                if (floatValue < -1.0f) {
                    floatValue = -1.0f;
                }
                if (floatValue > 1.0f) {
                    floatValue = 1.0f;
                }
                return Float.valueOf(fq.a.b(Math.abs(floatValue)) * Math.signum(floatValue) * ((mm) obj).G(4.0f));
            default:
                p pVar = (p) obj3;
                pVar.removeOnAttachStateChangeListener((m5) obj2);
                o30.s(pVar).a.remove((v7) obj);
                return x31.a;
        }
    }
}
