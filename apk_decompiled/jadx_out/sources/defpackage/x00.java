package defpackage;

import java.io.Serializable;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class x00 {
    public final /* synthetic */ int a;
    public final ty b;
    public final ty c;
    public final ty d;
    public final ty e;
    public final Serializable f;

    /* JADX WARN: Multi-variable type inference failed */
    public x00(x00[] x00VarArr) {
        int i = 0;
        this.a = 0;
        this.f = x00VarArr;
        int length = x00VarArr.length;
        ty[] tyVarArr = new ty[length];
        for (int i2 = 0; i2 < length; i2++) {
            tyVarArr[i2] = ((x00[]) this.f)[i2].b();
        }
        int i3 = 1;
        this.b = new ty(1, new c51(tyVarArr, i));
        int length2 = ((x00[]) this.f).length;
        ty[] tyVarArr2 = new ty[length2];
        for (int i4 = 0; i4 < length2; i4++) {
            tyVarArr2[i4] = ((x00[]) this.f)[i4].d();
        }
        this.c = new ty(0, new sy(tyVarArr2, i));
        int length3 = ((x00[]) this.f).length;
        ty[] tyVarArr3 = new ty[length3];
        for (int i5 = 0; i5 < length3; i5++) {
            tyVarArr3[i5] = ((x00[]) this.f)[i5].c();
        }
        this.d = new ty(1, new c51(tyVarArr3, i3));
        int length4 = ((x00[]) this.f).length;
        ty[] tyVarArr4 = new ty[length4];
        for (int i6 = 0; i6 < length4; i6++) {
            tyVarArr4[i6] = ((x00[]) this.f)[i6].a();
        }
        this.e = new ty(0, new sy(tyVarArr4, i3));
    }

    public final ty a() {
        int i = this.a;
        return this.e;
    }

    public final ty b() {
        int i = this.a;
        return this.b;
    }

    public final ty c() {
        int i = this.a;
        return this.d;
    }

    public final ty d() {
        int i = this.a;
        return this.c;
    }

    public final String toString() {
        int i = this.a;
        Object obj = this.f;
        switch (i) {
            case 0:
                StringBuilder sb = new StringBuilder();
                sb.append((CharSequence) "innermostOf(");
                int i2 = 0;
                for (x00 x00Var : (x00[]) obj) {
                    i2++;
                    if (i2 > 1) {
                        sb.append((CharSequence) ", ");
                    }
                    o30.f(sb, x00Var, null);
                }
                sb.append((CharSequence) ")");
                return sb.toString();
            default:
                return "RectRulers(" + ((String) obj) + ')';
        }
    }

    public x00(String str) {
        this.a = 1;
        this.f = str;
        this.b = new ty(1, null);
        this.c = new ty(0, null);
        this.d = new ty(1, null);
        this.e = new ty(0, null);
    }
}
