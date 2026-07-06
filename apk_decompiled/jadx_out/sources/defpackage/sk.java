package defpackage;

import java.util.Arrays;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class sk extends sz0 implements gv {
    public final /* synthetic */ al i;
    public final /* synthetic */ float j;
    public final /* synthetic */ hk k;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public sk(al alVar, float f, hk hkVar, ij ijVar) {
        super(1, ijVar);
        this.i = alVar;
        this.j = f;
        this.k = hkVar;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        float f = this.j;
        hk hkVar = this.k;
        sk skVar = new sk(this.i, f, hkVar, (ij) obj);
        x31 x31Var = x31.a;
        skVar.k(x31Var);
        return x31Var;
    }

    @Override // defpackage.s9
    public final Object k(Object obj) {
        o30.x(obj);
        al alVar = this.i;
        fm fmVar = (fm) alVar.r.a;
        x41 x41Var = fmVar.a;
        bl[] blVarArr = x41Var.d;
        Arrays.fill(blVarArr, 0, blVarArr.length, (Object) null);
        x41Var.e = 0;
        x41 x41Var2 = fmVar.b;
        bl[] blVarArr2 = x41Var2.d;
        Arrays.fill(blVarArr2, 0, blVarArr2.length, (Object) null);
        x41Var2.e = 0;
        fmVar.c = 0L;
        hk hkVar = alVar.a;
        f31.G(hkVar, null, new i20(alVar, null, 3), 3);
        qk qkVar = new qk(alVar, ((Number) n30.m(new Float(this.j), alVar.b)).floatValue(), null, 0);
        hk hkVar2 = this.k;
        f31.G(hkVar2, null, qkVar, 3);
        if (((Number) alVar.m.d()).floatValue() != 0.0f) {
            f31.G(hkVar2, null, new rk(alVar, null, 0), 3);
        }
        f31.G(hkVar, null, new d(alVar, null, 4), 3);
        return x31.a;
    }
}
