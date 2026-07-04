package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class vk extends z30 implements vu {
    public final /* synthetic */ int f;
    public final /* synthetic */ al g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ vk(al alVar, int i) {
        super(0);
        this.f = i;
        this.g = alVar;
    }

    @Override // defpackage.vu
    public final Object a() {
        int i = this.f;
        al alVar = this.g;
        switch (i) {
            case 0:
                alVar.e.e(alVar);
                f31.G(alVar.a, null, new d(alVar, null, 4), 3);
                return x31.a;
            case 1:
                return (Float) alVar.l.d();
            case 2:
                return dy.a(dy.e, 0.0f, 0.0f, alVar.b(), 11);
            case 3:
                return new sv0(0L, alVar.b(), 23);
            case 4:
                float b = alVar.b();
                return new y00(8.0f * b, b, 22);
            case 5:
                return Float.valueOf(d20.z(1.0f, 1.2f, alVar.b()));
            case 6:
                return dy.a(dy.e, 0.0f, 0.0f, alVar.b(), 11);
            case 7:
                float b2 = alVar.b();
                dy dyVar = dy.f;
                return dy.a(dyVar, dyVar.a / 1.5f, dyVar.b / 1.5f, b2, 8);
            case 8:
                float b3 = alVar.b();
                return new y00(4.0f * b3, b3, 22);
            case 9:
                float b4 = alVar.b();
                dy dyVar2 = dy.f;
                return dy.a(dyVar2, dyVar2.a / 1.5f, dyVar2.b / 1.5f, b4, 8);
            default:
                float b5 = alVar.b();
                return new y00(4.0f * b5, b5, 22);
        }
    }
}
