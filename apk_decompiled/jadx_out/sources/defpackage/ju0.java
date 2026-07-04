package defpackage;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Shader;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ju0 {
    public final o5 a;
    public final BitmapShader b;

    public ju0(o5 o5Var) {
        this.a = o5Var;
        Bitmap j = f31.j(o5Var);
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        this.b = new BitmapShader(j, tileMode, tileMode);
    }
}
