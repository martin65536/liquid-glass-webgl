package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class tj extends z30 implements gv {
    public final /* synthetic */ int f;
    public final /* synthetic */ long g;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ tj(int i, long j) {
        super(1);
        this.f = i;
        this.g = j;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        int i = this.f;
        x31 x31Var = x31.a;
        switch (i) {
            case 0:
                up upVar = (up) obj;
                upVar.getClass();
                d3.q(upVar, this.g, 0L, 0.0f, 0, 126);
                return x31Var;
            case 1:
                b50 b50Var = (b50) obj;
                b50Var.getClass();
                b50Var.r();
                d3.q(b50Var, this.g, 0L, 0.0f, 0, 126);
                return x31Var;
            case 2:
                up upVar2 = (up) obj;
                upVar2.getClass();
                d3.q(upVar2, this.g, 0L, 0.0f, 0, 126);
                return x31Var;
            case 3:
                up upVar3 = (up) obj;
                upVar3.getClass();
                d3.q(upVar3, this.g, 0L, 0.0f, 0, 126);
                return x31Var;
            case 4:
                up upVar4 = (up) obj;
                upVar4.getClass();
                d3.q(upVar4, this.g, 0L, 0.0f, 0, 126);
                return x31Var;
            case 5:
                np npVar = (np) obj;
                npVar.getClass();
                o4.o(npVar, npVar.e * 4.0f);
                g30.F(npVar, "AlphaMask", "\n    uniform shader content;\n    \n    uniform float2 size;\n    layout(color) uniform half4 tint;\n    uniform float tintIntensity;\n    \n    half4 main(float2 coord) {\n        float blurAlpha = smoothstep(size.y, size.y * 0.5, coord.y);\n        float tintAlpha = smoothstep(size.y, size.y * 0.5, coord.y);\n        return mix(content.eval(coord) * blurAlpha, tint * tintAlpha, tintIntensity);\n    }", new sj(npVar, this.g, 1));
                return x31Var;
            case 6:
                up upVar5 = (up) obj;
                upVar5.getClass();
                d3.q(upVar5, this.g, 0L, 0.0f, 0, 126);
                return x31Var;
            default:
                up upVar6 = (up) obj;
                upVar6.getClass();
                d3.q(upVar6, this.g, 0L, 0.0f, 0, 126);
                return x31Var;
        }
    }
}
