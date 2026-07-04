package defpackage;

import android.graphics.Bitmap;
import android.graphics.RuntimeShader;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class iu0 extends z30 implements gv {
    public final /* synthetic */ ju0 f;
    public final /* synthetic */ np g;
    public final /* synthetic */ float h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public iu0(ju0 ju0Var, np npVar, float f) {
        super(1);
        this.f = ju0Var;
        this.g = npVar;
        this.h = f;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        h6 h6Var = (h6) obj;
        h6Var.getClass();
        RuntimeShader runtimeShader = h6Var.a;
        ju0 ju0Var = this.f;
        runtimeShader.setInputBuffer("sdfTex", ju0Var.b);
        np npVar = this.g;
        h6Var.a.setFloatUniform("size", Float.intBitsToFloat((int) (npVar.g >> 32)), Float.intBitsToFloat((int) (npVar.g & 4294967295L)));
        Bitmap bitmap = ju0Var.a.a;
        h6Var.a.setFloatUniform("sdfTexSize", bitmap.getWidth(), bitmap.getHeight());
        h6Var.a.setFloatUniform("refractionHeight", this.h);
        h6Var.a.setFloatUniform("lightAngle", 45.0f);
        return x31.a;
    }
}
