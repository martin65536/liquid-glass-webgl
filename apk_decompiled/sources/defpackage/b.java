package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class b implements vu {
    public final /* synthetic */ int e;
    public final /* synthetic */ de f;

    public /* synthetic */ b(de deVar, int i) {
        this.e = i;
        this.f = deVar;
    }

    @Override // defpackage.vu
    public final Object a() {
        im imVar;
        int i = this.e;
        de deVar = this.f;
        switch (i) {
            case 0:
                a00 a00Var = (a00) n20.p(deVar, wz.a);
                if (a00Var == null) {
                    t00.a("clickable only supports IndicationNodeFactory instances provided to LocalIndication, but Indication was provided instead. Either migrate the Indication implementation to implement IndicationNodeFactory, or use the other clickable overload that takes an Indication parameter, and explicitly pass LocalIndication.current there. The Indication instance provided here was: " + a00Var);
                }
                a00 a00Var2 = deVar.B;
                deVar.B = a00Var;
                if (a00Var2 != null && !o20.e(a00Var, a00Var2) && ((imVar = deVar.D) != null || !deVar.K)) {
                    if (imVar != null) {
                        deVar.E0(imVar);
                    }
                    deVar.D = null;
                    deVar.J0();
                }
                return x31.a;
            default:
                deVar.z.a();
                return Boolean.TRUE;
        }
    }
}
