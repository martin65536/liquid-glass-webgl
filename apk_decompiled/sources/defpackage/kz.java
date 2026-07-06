package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class kz {
    public static final /* synthetic */ int e = 0;
    public final boolean a;
    public final int b;
    public final int c;
    public final ua0 d;

    static {
        new kz();
    }

    public kz() {
        ua0 ua0Var = ua0.g;
        this.a = true;
        this.b = 1;
        this.c = 1;
        this.d = ua0Var;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof kz) {
                kz kzVar = (kz) obj;
                if (this.a == kzVar.a && this.b == kzVar.b && this.c == kzVar.c && o20.e(this.d, kzVar.d)) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return true;
    }

    public final int hashCode() {
        int i;
        if (this.a) {
            i = 1231;
        } else {
            i = 1237;
        }
        return this.d.e.hashCode() + ((((((1188757 + i) * 31) + this.b) * 31) + this.c) * 961);
    }

    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder("ImeOptions(singleLine=false, capitalization=");
        String str2 = "None";
        sb.append((Object) "None");
        sb.append(", autoCorrect=");
        sb.append(this.a);
        sb.append(", keyboardType=");
        int i = this.b;
        if (i == 0) {
            str = "Unspecified";
        } else if (i == 1) {
            str = "Text";
        } else if (i == 2) {
            str = "Ascii";
        } else if (i == 3) {
            str = "Number";
        } else if (i == 4) {
            str = "Phone";
        } else if (i == 5) {
            str = "Uri";
        } else if (i == 6) {
            str = "Email";
        } else if (i == 7) {
            str = "Password";
        } else if (i == 8) {
            str = "NumberPassword";
        } else if (i != 9) {
            str = "Invalid";
        } else {
            str = "Decimal";
        }
        sb.append((Object) str);
        sb.append(", imeAction=");
        int i2 = this.c;
        if (i2 == -1) {
            str2 = "Unspecified";
        } else if (i2 != 0) {
            if (i2 == 1) {
                str2 = "Default";
            } else if (i2 == 2) {
                str2 = "Go";
            } else if (i2 == 3) {
                str2 = "Search";
            } else if (i2 == 4) {
                str2 = "Send";
            } else if (i2 == 5) {
                str2 = "Previous";
            } else if (i2 == 6) {
                str2 = "Next";
            } else if (i2 != 7) {
                str2 = "Invalid";
            } else {
                str2 = "Done";
            }
        }
        sb.append((Object) str2);
        sb.append(", platformImeOptions=null, hintLocales=");
        sb.append(this.d);
        sb.append(')');
        return sb.toString();
    }
}
