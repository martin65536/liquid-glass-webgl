package defpackage;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class hf0 {
    public static final hf0 e;
    public static final /* synthetic */ hf0[] f;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [hf0, java.lang.Enum] */
    static {
        ?? r0 = new Enum("Default", 0);
        e = r0;
        f = new hf0[]{r0, new Enum("UserInput", 1), new Enum("PreventUserInput", 2)};
    }

    public static hf0 valueOf(String str) {
        return (hf0) Enum.valueOf(hf0.class, str);
    }

    public static hf0[] values() {
        return (hf0[]) f.clone();
    }
}
