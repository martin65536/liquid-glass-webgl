package defpackage;

import android.text.TextUtils;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class wq implements vq {
    public final /* synthetic */ int e;
    public final String f;

    public /* synthetic */ wq(String str, int i) {
        this.e = i;
        this.f = str;
    }

    @Override // defpackage.vq
    public boolean h(CharSequence charSequence, int i, int i2, n31 n31Var) {
        if (TextUtils.equals(charSequence.subSequence(i, i2), this.f)) {
            n31Var.c = (n31Var.c & 3) | 4;
            return false;
        }
        return true;
    }

    public String toString() {
        switch (this.e) {
            case 1:
                return "<" + this.f + '>';
            default:
                return super.toString();
        }
    }

    @Override // defpackage.vq
    public Object a() {
        return this;
    }
}
