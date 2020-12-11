window.onload = async function () {
    //await LoadDocument();
    LoginWindow();
};

async function LoadDocument() {
    if (document.readyState === "complete") {
        return;
    }

    let promiseResolve = null;
    const promise = new Promise((resolve, _reject) => {
        promiseResolve = resolve;
    });
    window.addEventListener("load", () => { promiseResolve(); });

    await promise;
}

function LoginWindow()
{
    const info = {
        login: "",
        selectedProject : "-1"
    }
    ShowWindow("login_window");
    document.getElementById("login_button").onclick = () => {

       // VERIFICAR SE USUARIO EXISTE 
        info.login = document.getElementById("login").value;
        console.log("LOGIN: " + login);
        HideWindow("login_window");
        MainWindow(info);
    }
}

function MainWindow(info)
{
    currentWindow = ShowWindow('main_window');
    let i = 0;
    for (const button of document.querySelectorAll("button.project_button")) {
        const currentIndex = i;
        button.textContent = data.projects_names[currentIndex];
        button.onclick = () => {
            console.log("CLICKED");
            info.selectedProject = currentIndex;
            ConfirmationPopup(info);
        };
        i++;
    }
}

function ConfirmationPopup(info)
{
    currentWindow = ShowWindow('validation_popup');
    document.getElementById("voting_project_name").textContent = data.projects_names[info.selectedProject];
    console.log(info.selectedProject);
    document.getElementById('yes_button_popup').onclick = () => {
        console.log("OK POPUP");
        HideWindow('main_window');
        HideWindow('validation_popup');
        data.projects_votes[info.selectedProject] += 1;
        HistogramWindow(info);
    }
    document.getElementById('no_button_popup').onclick = () => {
        HideWindow('validation_popup');
        console.log("CANCEL POPUP");
        info.selectedProject = -1;
    }
}

function HistogramWindow(info)
{
    currentWindow = ShowWindow('histogram_window');
    let i = 0;
    for (const element of document.querySelectorAll("div.single_histogram_div")) {
        const currentIndex = i;
        const height = 30 * data.projects_votes[currentIndex] + 10;
        element.getElementsByClassName("histogram_square")[0].style.height = height + "px";
        console.log(element.getElementsByClassName("histogram_square"));
        element.getElementsByClassName("project_histogram_name")[0].textContent = data.projects_names[currentIndex] + " \n" + data.projects_votes[currentIndex];
        i++;
    }
}

function ShowWindow(windowName){
    currentWindow = document.getElementById(windowName);
    currentWindow.style.display = 'block';
    currentWindow.style.opacity = 1;
    return currentWindow;
}

function HideWindow(windowName){
    currentWindow = document.getElementById(windowName);
    currentWindow.style.display = 'none';
    currentWindow.style.opacity = 0;
    return currentWindow;
}